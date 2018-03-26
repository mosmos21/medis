package jp.co.unirita.medis.logic.system;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.documenttag.DocumentTag;
import jp.co.unirita.medis.domain.documenttag.DocumentTagRepository;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.templatetag.TemplateTag;
import jp.co.unirita.medis.domain.templatetag.TemplateTagRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.logic.template.TemplateLogic;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class NotificationLogic {

	private static final Logger logger = LoggerFactory.getLogger(TemplateLogic.class);

    @Autowired
    MailLogic mailLogic;

    @Autowired
    DocumentInfoRepository documentInfoRepository;
    @Autowired
    TemplateTagRepository templateTagRepository;
    @Autowired
    DocumentTagRepository documentTagRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    NotificationConfigRepository notificationConfigRepository;
    @Autowired
    UserDetailRepository userDetailRepository;

    /**
     * 新規文書が投稿されたときに、文書についたタグを監視しているユーザにメールを送信する
     * @param employeeNumber 文書を投稿したユーザの社員番号
     * @param documentId 投稿した文書のID
     */
    public void documentContributionNotification(String employeeNumber, String documentId) {
    	try {
    		UserDetail detail = userDetailRepository.findOne(employeeNumber);
            String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
            String documentName = documentInfoRepository.findOne(documentId).getDocumentName();
            Stream<String> mailAddressStream = getNotificationMailAdressStream(documentId);

            mailAddressStream.forEach(address -> {
                mailLogic.sendDocumentContributionNotification(address, documentId, documentName, employeeNumber, name);
            });
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: NotificationLogic, method: documentContributionNotification]");
			throw new DBException("DB Runtime Error[class: NotificationLogic, method: documentContributionNotification]");
		}
    }

    /**
     * 投稿済みの文書が更新されたときに、文書についたタグを監視しているユーザにメールを送信する
     * @param employeeNumber 文書を更新したユーザの社員番号
     * @param documentId　更新した文書のID
     */
    public void documentUpdateNotification(String employeeNumber, String documentId) {
    	try {
    		UserDetail detail = userDetailRepository.findOne(employeeNumber);
            String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
            String documentName = documentInfoRepository.findOne(documentId).getDocumentName();
            Stream<String> mailAddressStream = getNotificationMailAdressStream(documentId);

            mailAddressStream.forEach(address -> {
                mailLogic.sendDocumentUpdateNotification(address, documentId, documentName, employeeNumber, name);
            });
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: NotificationLogic, method: documentUpdateNotification]");
			throw new DBException("DB Runtime Error[class: NotificationLogic, method: documentUpdateNotification]");
		}
    }

    /**
     * コメントを書いたと気に文書の投稿者に通知を送る
     * @param employeeNumber コメントを書いた人の社員番号
     * @param documentId コメントを書いた文書ID
     */
    public void commentNotification(String employeeNumber, String documentId) {
    	try {
    		UserDetail detail = userDetailRepository.findOne(employeeNumber);
            String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
            String mailAddress = userDetailRepository.findOne(documentInfoRepository.findOne(documentId).getEmployeeNumber()).getMailaddress();
            String documentName = documentInfoRepository.findOne(documentId).getDocumentName();

            mailLogic.sendCommentNotification(mailAddress, documentId, documentName, employeeNumber, name);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: NotificationLogic, method: commentNotification]");
			throw new DBException("DB Runtime Error[class: NotificationLogic, method: commentNotification]");
		}
    }

    /**
     * 書かれたコメントに既読がついたときにコメント記入者に通知を送る
     * @param commentId 既読にしたコメントID
     */
    public void commentReadNotification(String commentId) {
    	try {
    		Comment comment = commentRepository.findOne(commentId);
            String mailAddress = userDetailRepository.findOne(comment.getEmployeeNumber()).getMailaddress();

            DocumentInfo info = documentInfoRepository.findOne(commentRepository.findOne(commentId).getDocumentId());
            UserDetail detail = userDetailRepository.findOne(info.getEmployeeNumber());
            String name = detail.getLastName() + " " + detail.getFirstName();

            mailLogic.sendCommentReadNotification(mailAddress, info.getDocumentId(), info.getDocumentName(), info.getEmployeeNumber(), name);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: NotificationLogic, method: commentReadNotification]");
			throw new DBException("DB Runtime Error[class: NotificationLogic, method: commentReadNotification]");
		}
    }

    // ドキュメントについているタグを監視している人のメールのリスト
    private Stream<String> getNotificationMailAdressStream(String documentId) {
    	try {
    		String templateId = documentInfoRepository.findOne(documentId).getTemplateId();

            List<String> documentTagList = documentTagRepository.findByDocumentId(documentId).stream()
                    .map(DocumentTag::getTagId).collect(Collectors.toList());
            List<String> templateTagList = templateTagRepository.findByTemplateId(templateId).stream()
                    .map(TemplateTag::getTagId).collect(Collectors.toList());
            Stream<String> employeeNumberStream1 = notificationConfigRepository.findByTagIdIn(documentTagList).stream()
                    .filter(NotificationConfig::isMailNotification)
                    .map(NotificationConfig::getEmployeeNumber);
            Stream<String> emploueeNumberStream2 = notificationConfigRepository.findByTagIdIn(templateTagList).stream()
                    .filter(NotificationConfig::isMailNotification)
                    .map(NotificationConfig::getEmployeeNumber);

            return Stream.concat(employeeNumberStream1, emploueeNumberStream2).distinct()
                    .map(userDetailRepository::findOne)
                    .map(UserDetail::getMailaddress);
    	} catch (DBException e) {
    		logger.error("DB Runtime Error[class: NotificationLogic, method: getNotificationMailAdressStream]");
			throw new DBException("DB Runtime Error[class: NotificationLogic, method: getNotificationMailAdressStream]");
		}
    }

    public static void main(String[] args) {
        new NotificationLogic().getNotificationMailAdressStream("d0000000000");
    }
}
