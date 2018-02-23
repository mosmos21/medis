package jp.co.unirita.medis.logic;

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
		List<Comment> commentList = new ArrayList<>();
		List<String> commentIdList = new ArrayList<>();
		List<String> employeeNumberList = new ArrayList<>();
		List<UserDetail> userdetailList = new ArrayList<>();
		List<CommentInfoForm> result = new ArrayList<>();

		commentList = commentRepository.findByDocumentIdOrderByCommentDateAsc(documentId);

		// comment_id取得
		for (int i = 0; i < commentList.size(); i++) {
			Comment count = commentList.get(i);
			commentIdList.add(count.getCommentId());
		}

		// employee_number取得
		for (int i = 0; i < commentIdList.size(); i++) {
			commentList = commentRepository.findByCommentId(commentIdList.get(i));
			employeeNumberList.add(commentList.get(0).getEmployeeNumber());
		}

		// レスポンスJSON作成
		for (int i = 0; i < employeeNumberList.size(); i++) {
			CommentInfoForm commentinfoform = new CommentInfoForm();

			commentList = commentRepository.findByCommentId(commentIdList.get(i));
			userdetailList = userDetailRepository.findAllByEmployeeNumber(employeeNumberList.get(i));

			commentinfoform.commentDate = commentList.get(0).getCommentDate();
			commentinfoform.lastName = userdetailList.get(0).getLastName();
			commentinfoform.firstName = userdetailList.get(0).getFirstName();
			commentinfoform.isIcon = userdetailList.get(0).isIcon();
			commentinfoform.isRead = commentList.get(0).isRead();
			commentinfoform.commentContent = commentList.get(0).getValue();
			result.add(commentinfoform);
			System.out.println(result);
		}

		return result;

	}

	public void alreadyRead(String documentId, String commentId) {
		List<Comment> commentList = new ArrayList<>();
		List<UserDetail> toUserDetailList = new ArrayList<>();
		List<UserDetail> authorUserDetailList = new ArrayList<>();
		List<DocumentInfo> documentInfoList = new ArrayList<>();

		commentList = commentRepository.findByCommentId(commentId);
			toUserDetailList = userDetailRepository.findAllByEmployeeNumber(commentList.get(0).getEmployeeNumber());
			documentInfoList = documentInfoRepository.findByDocumentId(commentList.get(0).getDocumentId());
			authorUserDetailList = userDetailRepository
				.findAllByEmployeeNumber(documentInfoList.get(0).getEmployeeNumber());

		String mailaddress = toUserDetailList.get(0).getMailaddress();
		String documentName = documentInfoList.get(0).getDocumentName();
		String lastName = authorUserDetailList.get(0).getLastName();
		String firstName = authorUserDetailList.get(0).getFirstName();

		//Readをtrueにする
		Comment comment = new Comment();

		comment.setCommentId(commentId);
		comment.setDocumentId(documentId);
		comment.setCommentDate(commentList.get(0).getCommentDate());
		comment.setEmployeeNumber(commentList.get(0).getEmployeeNumber());
		comment.setValue(commentList.get(0).getValue());
		comment.setRead(true);

		commentRepository.saveAndFlush(comment);

		//メール送信
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailaddress);
		msg.setSubject("【MEDIS】コメントが読まれました"); // タイトルの設定
		msg.setText(lastName + firstName + "さんが作成した" + documentName + "のコメントが読まれました。\r\n\r\n");

		this.sender.send(msg);
	}

}
