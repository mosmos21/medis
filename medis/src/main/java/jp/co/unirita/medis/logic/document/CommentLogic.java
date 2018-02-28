package jp.co.unirita.medis.logic.document;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.CommentCreateForm;
import jp.co.unirita.medis.form.CommentInfoForm;

@Service
public class CommentLogic {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;
	@Autowired
	private MailSender sender;

	public List<CommentInfoForm> getCommentInfo(String documentId) {
		Comment commentList;
		List<String> commentIdList = new ArrayList<>();
		List<String> employeeNumberList = new ArrayList<>();
		UserDetail userdetailList;
		List<CommentInfoForm> result = new ArrayList<>();

		List<Comment> commentLength = new ArrayList<>();

		commentLength = commentRepository.findByDocumentIdOrderByCommentDateAsc(documentId);

		// comment_id取得
		for (int i = 0; i < commentLength.size(); i++) {
			Comment count = commentLength.get(i);
			commentIdList.add(count.getCommentId());
		}

		// employee_number取得
		for (int i = 0; i < commentIdList.size(); i++) {
			commentList = commentRepository.findByCommentId(commentIdList.get(i));
			employeeNumberList.add(commentList.getEmployeeNumber());
		}

		// レスポンスJSON作成
		for (int i = 0; i < employeeNumberList.size(); i++) {
			CommentInfoForm commentinfoform = new CommentInfoForm();

			commentList = commentRepository.findByCommentId(commentIdList.get(i));
			userdetailList = userDetailRepository.findOne(employeeNumberList.get(i));

			commentinfoform.commentDate = commentList.getCommentDate();
			commentinfoform.lastName = userdetailList.getLastName();
			commentinfoform.firstName = userdetailList.getFirstName();
			commentinfoform.isIcon = userdetailList.isIcon();
			commentinfoform.isRead = commentList.isRead();
			commentinfoform.commentContent = commentList.getValue();
			result.add(commentinfoform);
			System.out.println(result);
		}

		return result;

	}

	public void alreadyRead(String documentId, String commentId) {
		Comment commentList;
		UserDetail toUserDetail;
		UserDetail authorUserDetail;
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		commentList = commentRepository.findByCommentId(commentId);
		toUserDetail = userDetailRepository.findOne(commentList.getEmployeeNumber());
		documentInfoList = documentInfoRepository.findByDocumentId(commentList.getDocumentId());
		authorUserDetail = userDetailRepository.findOne(documentInfoList.get(0).getEmployeeNumber());

		String mailaddress = toUserDetail.getMailaddress();
		String documentName = documentInfoList.get(0).getDocumentName();
		String lastName = authorUserDetail.getLastName();
		String firstName = authorUserDetail.getFirstName();

		// Readをtrueにする
		Comment comment = new Comment();

		comment.setCommentId(commentId);
		comment.setDocumentId(documentId);
		comment.setCommentDate(commentList.getCommentDate());
		comment.setEmployeeNumber(commentList.getEmployeeNumber());
		comment.setValue(commentList.getValue());
		comment.setRead(true);

		commentRepository.saveAndFlush(comment);

		// メール送信
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailaddress);
		msg.setSubject("【MEDIS】コメントが読まれました"); // タイトルの設定
		msg.setText(lastName + firstName + "さんが作成した" + documentName + "のコメントが読まれました。\r\n\r\n");

		this.sender.send(msg);
	}

	public void sava(String documentId, CommentCreateForm postData) {
		List<Comment> commentList = commentRepository.findByOrderByCommentIdDesc();
		String lastCommentId = commentList.get(0).getCommentId();
		String head = lastCommentId.substring(0, 1);
		String body = lastCommentId.substring(1, 11);
		int temp = Integer.parseInt(body);
		temp++;
		body = String.format("%010d", temp);
		lastCommentId = head + body;

		Timestamp commentDate = new Timestamp(System.currentTimeMillis());
		String employeeNumber = postData.getEmployeeNumber();
		String value = postData.getValue();
		boolean read = false;

		Comment comment = new Comment(lastCommentId, documentId, commentDate, employeeNumber, value, read);
		commentRepository.save(comment);

	}

}
