package jp.co.unirita.medis.logic.document;

import java.lang.invoke.MethodHandles;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	DocumentInfoRepository documentInfoRepository;

	private MailSender sender;

	public List<CommentInfoForm> getCommentInfo(String documentId) {

		List<String> commentIdList = new ArrayList<>();
		List<String> employeeNumberList = new ArrayList<>();
		List<CommentInfoForm> result = new ArrayList<>();

		List<Comment> commentLength = commentRepository.findByDocumentIdOrderByCommentDateAsc(documentId);
		// comment_id取得
		for (Comment add : commentLength) {
			commentIdList.add(add.getCommentId());
		}

		// employee_number取得
		for (Comment add : commentLength) {
			employeeNumberList.add(add.getEmployeeNumber());
		}

		// レスポンスJSON作成
		for (int i=0;i<employeeNumberList.size();i++) {
			CommentInfoForm commentinfoform = new CommentInfoForm();

			Comment commentList = commentRepository.findOne(commentIdList.get(i));
			UserDetail userdetailList = userDetailRepository.findOne(employeeNumberList.get(i));

			commentinfoform.setCommentDate(commentList.getCommentDate());
			commentinfoform.setLastName(userdetailList.getLastName());
			commentinfoform.setFirstName(userdetailList.getFirstName());
			commentinfoform.setIcon(userdetailList.isIcon());
			commentinfoform.setRead(commentList.isRead());
			commentinfoform.setCommentContent(commentList.getValue());
			result.add(commentinfoform);
		}

		return result;

	}

	public void alreadyRead(String documentId, String commentId) {

		Comment commentList = commentRepository.findOne(commentId);
		UserDetail toUserDetail = userDetailRepository.findOne(commentList.getEmployeeNumber());
		List<DocumentInfo> documentInfoList = documentInfoRepository.findByDocumentId(commentList.getDocumentId());
		UserDetail authorUserDetail = userDetailRepository.findOne(documentInfoList.get(0).getEmployeeNumber());

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

	public void save(String documentId, CommentCreateForm postData) {
		List<Comment> commentList = commentRepository.findAll(new Sort(Sort.Direction.DESC, "commentId"));
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
