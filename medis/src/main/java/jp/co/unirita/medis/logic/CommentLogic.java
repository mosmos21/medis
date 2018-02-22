package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.CommentInfoForm;

@Service
public class CommentLogic {
	@Autowired
	CommentRepository commentRepository;
	@Autowired
	UserDetailRepository userdetailRepository;

	public List<CommentInfoForm> getCommentInfo(String documentId) {
		List<Comment> comment = new ArrayList<>();
		List<String> commentId = new ArrayList<>();
		List<String> employeeNumber = new ArrayList<>();
		List<UserDetail> userdetail = new ArrayList<>();
		List<CommentInfoForm> result = new ArrayList<>();

		comment = commentRepository.findByDocumentIdOrderByCommentDateAsc(documentId);

		// comment_id取得
		for (int i = 0; i < comment.size(); i++) {
			Comment count = comment.get(i);
			commentId.add(count.getCommentId());
		}

		// employee_number取得
		for (int i = 0; i < commentId.size(); i++) {
			comment = commentRepository.findByCommentId(commentId.get(i));
			employeeNumber.add(comment.get(0).getEmployeeNumber());
		}

		// レスポンスJSON作成
		for (int i = 0; i < employeeNumber.size(); i++) {
			CommentInfoForm commentinfoform = new CommentInfoForm();

			comment = commentRepository.findByCommentId(commentId.get(i));
			userdetail = userdetailRepository.findAllByEmployeeNumber(employeeNumber.get(i));

			commentinfoform.commentDate = comment.get(0).getCommentDate();
			commentinfoform.lastName = userdetail.get(0).getLastName();
			commentinfoform.firstName = userdetail.get(0).getFirstName();
			commentinfoform.isIcon = userdetail.get(0).isIcon();
			commentinfoform.isRead = comment.get(0).isRead();
			commentinfoform.commentContent = comment.get(0).getValue();
			result.add(commentinfoform);
			System.out.println(result);
		}

		return result;

	}

	public void alreadyRead(String documentId, String commentId) {
		List<Comment> commentList = commentRepository.findByCommentId(commentId);

		//boolean flug = commentList.get(0).isRead();

		Comment comment = new Comment();

		comment.setCommentId(commentId);
		comment.setDocumentId(documentId);
		/*
		 * if(flug) { comment.setRead(false); }else {
		 */
		comment.setRead(true);
		// }

		commentRepository.saveAndFlush(comment);
	}

}
