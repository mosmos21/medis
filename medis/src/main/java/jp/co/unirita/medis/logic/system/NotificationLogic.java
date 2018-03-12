package jp.co.unirita.medis.logic.system;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class NotificationLogic {

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
        UserDetail detail = userDetailRepository.findOne(employeeNumber);
        String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
        String documentName = documentInfoRepository.findOne(documentId).getDocumentName();
        Stream<String> mailAddressStream = getNotificationMailAdressStream(documentId);

        mailAddressStream.forEach(address -> {
            mailLogic.sendDocumentContributionNotification(address, documentId, documentName, employeeNumber, name);
        });
    }

    /**
     * 投稿済みの文書が更新されたときに、文書についたタグを監視しているユーザにメールを送信する
     * @param employeeNumber 文書を更新したユーザの社員番号
     * @param documentId　更新した文書のID
     */
    public void documentUpdateNotification(String employeeNumber, String documentId) {
        UserDetail detail = userDetailRepository.findOne(employeeNumber);
        String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
        String documentName = documentInfoRepository.findOne(documentId).getDocumentName();
        Stream<String> mailAddressStream = getNotificationMailAdressStream(documentId);

        mailAddressStream.forEach(address -> {
            mailLogic.sendDocumentUpdateNotification(address, documentId, documentName, employeeNumber, name);
        });
    }

    /**
     * コメントを書いたと気に文書の投稿者に通知を送る
     * @param employeeNumber コメントを書いた人の社員番号
     * @param documentId コメントを書いた文書ID
     */
    public void commentNotification(String employeeNumber, String documentId) {
        UserDetail detail = userDetailRepository.findOne(employeeNumber);
        String name = new StringBuilder().append(detail.getLastName()).append(" ").append(detail.getFirstName()).toString();
        String mailAddress = userDetailRepository.findOne(documentInfoRepository.findOne(documentId).getDocumentId()).getMailaddress();
        String documentName = documentInfoRepository.findOne(documentId).getDocumentName();

        mailLogic.sendCommentNotification(mailAddress, documentId, documentName, employeeNumber, name);
    }

    /**
     * 書かれたコメントに既読がついたときにコメント記入者に通知を送る
     * @param commentId 既読にしたコメントID
     */
    public void commentReadNotification(String commentId) {
        Comment comment = commentRepository.findOne(commentId);
        DocumentInfo info = documentInfoRepository.findOne(comment.getCommentId());
        UserDetail detail = userDetailRepository.findOne(comment.getEmployeeNumber());
        String name = detail.getLastName() + " " + detail.getFirstName();
        String mailAddress = userDetailRepository.findOne(info.getEmployeeNumber()).getMailaddress();

        mailLogic.sendCommentReadtNotification(mailAddress, info.getDocumentId(), info.getDocumentName(), comment.getEmployeeNumber(), name);
    }

    // ドキュメントについているタグを監視している人のメールのリスト
    private Stream<String> getNotificationMailAdressStream(String documentId) {
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
    }

    public static void main(String[] args) {
        new NotificationLogic().getNotificationMailAdressStream("d0000000000");
    }
}
