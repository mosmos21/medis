package jp.co.unirita.medis.logic.document;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.mail.MailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.CommentInfoForm;
import jp.co.unirita.medis.logic.system.NotificationLogic;
import jp.co.unirita.medis.util.exception.DBException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@Service
@Transactional(rollbackFor = Exception.class)
public class CommentLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	NotificationLogic notificationLogic;

	private MailSender sender;

	public List<CommentInfoForm> getCommentInfo(String documentId) {
		try {
			List<String> commentIdList = new ArrayList<>();
			List<String> employeeNumberList = new ArrayList<>();
			List<CommentInfoForm> commentInfoList = new ArrayList<>();

			List<Comment> commentList = commentRepository.findByDocumentIdOrderByCommentDateAsc(documentId);

			// comment_id取得
			for (Comment com : commentList) {
				commentIdList.add(com.getCommentId());
			}

			// employee_number取得
			for (Comment com : commentList) {
				employeeNumberList.add(com.getEmployeeNumber());
			}

			// レスポンスJSON作成
			for (int i = 0; i < employeeNumberList.size(); i++) {
				Comment comment = commentRepository.findOne(commentIdList.get(i));
				UserDetail userDetail = userDetailRepository.findOne(employeeNumberList.get(i));
				commentInfoList.add(createCommentInfoForm(comment, userDetail));
			}
			return commentInfoList;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: CommentLogic, method: getCommentInfo]");
			throw new DBException("DB Runtime Error[class: CommentLogic, method: getCommentInfo]");
		}
	}

	private CommentInfoForm createCommentInfoForm(Comment comment, UserDetail userDetail) {
		CommentInfoForm commentInfo = new CommentInfoForm();
		commentInfo.setCommentDate(comment.getCommentDate());
		commentInfo.setLastName(userDetail.getLastName());
		commentInfo.setFirstName(userDetail.getFirstName());
		commentInfo.setIcon(userDetail.isIcon());
		commentInfo.setRead(comment.isRead());
		commentInfo.setCommentContent(comment.getValue());
		commentInfo.setEmployeeNumber(userDetail.getEmployeeNumber());
		commentInfo.setCommentId(comment.getCommentId());
		return commentInfo;
	}

	public void alreadyRead(String documentId, String commentId) throws IdIssuanceUpperException {
		try {
			// Readをtrueにする
			Comment commentInfo = commentRepository.findOne(commentId);
			Comment comment = new Comment();

			comment.setCommentId(commentId);
			comment.setDocumentId(documentId);
			comment.setCommentDate(commentInfo.getCommentDate());
			comment.setEmployeeNumber(commentInfo.getEmployeeNumber());
			comment.setValue(commentInfo.getValue());
			comment.setRead(true);
			commentRepository.saveAndFlush(comment);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: CommentLogic, method: alreadyRead]");
			throw new DBException("DB Runtime Error[class: CommentLogic, method: alreadyRead]");
		}
	}

	/*
	 * // メール送信 public void sendMail(String mailaddress, String lastName, String
	 * firstName, String documentName) { SimpleMailMessage msg = new
	 * SimpleMailMessage(); msg.setTo(mailaddress);
	 * msg.setSubject("【MEDIS】コメントが読まれました"); // タイトルの設定 msg.setText(lastName +
	 * firstName + "さんが作成した" + documentName + "のコメントが読まれました。\r\n\r\n");
	 * this.sender.send(msg); }
	 */
	// 最新のIDを生成
	 public synchronized String getNewCommentId() throws IdIssuanceUpperException {
		try {
			List<Comment> commentList = commentRepository.findAll(new Sort(Sort.Direction.DESC, "commentId"));
			if (commentList.size() == 0) {
				return "o0000000000";
			}
			long idNum = Long.parseLong(commentList.get(0).getCommentId().substring(1));
			if (idNum == 9999999999L) {
				throw new IdIssuanceUpperException("IDの発行限界");
			}
			return String.format("o%010d", idNum + 1);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: CommentLogic, method: getNewCommentId]");
			throw new DBException("DB Runtime Error[class: CommentLogic, method: getNewCommentId]");
		}
	}

	public CommentInfoForm save(String documentId, String employeeNumber, Map<String, String> value)
			throws IdIssuanceUpperException {
		try {
			Timestamp commentDate = new Timestamp(System.currentTimeMillis());
			boolean read = false;
			Comment comment = new Comment(getNewCommentId(), documentId, commentDate, employeeNumber, value.get("value"),
					read);
			commentRepository.save(comment);
			UserDetail userDetail = userDetailRepository.findOne(employeeNumber);

			return createCommentInfoForm(comment, userDetail);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: CommentLogic, method: save]");
			throw new DBException("DB Runtime Error[class: CommentLogic, method: save]");
		}
	}
}
