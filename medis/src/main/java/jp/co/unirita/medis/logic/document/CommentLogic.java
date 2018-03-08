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
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.documentInfo.DocumentInfoRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.document.CommentInfoForm;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

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
		for (int i=0;i<employeeNumberList.size();i++) {
			Comment comment = commentRepository.findOne(commentIdList.get(i));
			UserDetail userDetail = userDetailRepository.findOne(employeeNumberList.get(i));
			commentInfoList.add(createCommentInfoForm(comment, userDetail));
		}
		return commentInfoList;
	}


	private CommentInfoForm createCommentInfoForm(Comment comment, UserDetail userDetail) {
		CommentInfoForm commentInfo = new CommentInfoForm();
		commentInfo.setCommentDate(comment.getCommentDate());
		commentInfo.setLastName(userDetail.getLastName());
		commentInfo.setFirstName(userDetail.getFirstName());
		commentInfo.setIcon(userDetail.isIcon());
		commentInfo.setRead(comment.isRead());
		commentInfo.setCommentContent(comment.getValue());
		return commentInfo;
	}


	public void alreadyRead(String documentId, String commentId) {

		Comment commentInfo = commentRepository.findOne(commentId);
		UserDetail toUserDetail = userDetailRepository.findOne(commentInfo.getEmployeeNumber());
		DocumentInfo documentInfo = documentInfoRepository.findOne(commentInfo.getDocumentId());
		UserDetail authorUserDetail = userDetailRepository.findOne(documentInfo.getEmployeeNumber());

		String mailaddress = toUserDetail.getMailaddress();
		String documentName = documentInfo.getDocumentName();
		String lastName = authorUserDetail.getLastName();
		String firstName = authorUserDetail.getFirstName();

		// Readをtrueにする
		Comment comment = new Comment();

		comment.setCommentId(commentId);
		comment.setDocumentId(documentId);
		comment.setCommentDate(commentInfo.getCommentDate());
		comment.setEmployeeNumber(commentInfo.getEmployeeNumber());
		comment.setValue(commentInfo.getValue());
		comment.setRead(true);
		commentRepository.saveAndFlush(comment);

		sendMail(mailaddress, lastName, firstName, documentName);
	}


	//メール送信
	public void sendMail(String mailaddress, String lastName, String firstName, String documentName) {
		SimpleMailMessage msg = new SimpleMailMessage();
		msg.setTo(mailaddress);
		msg.setSubject("【MEDIS】コメントが読まれました"); // タイトルの設定
		msg.setText(lastName + firstName + "さんが作成した" + documentName + "のコメントが読まれました。\r\n\r\n");
		this.sender.send(msg);
	}


	//最新のIDを生成
	public String getNewCommentId() throws IdIssuanceUpperException{
		List<Comment> commentList = commentRepository.findAll(new Sort(Sort.Direction.DESC, "commentId"));
		if(commentList.size() == 0) {
            return "o0000000000";
        }
        long idNum = Long.parseLong(commentList.get(0).getCommentId().substring(1));
        if(idNum == 9999999999L) {
            throw new IdIssuanceUpperException("IDの発行限界");
        }
        return String.format("o%010d", idNum + 1);
    }


	public CommentInfoForm save(String documentId, String employeeNumber, Map<String, String> value) throws IdIssuanceUpperException {
		Timestamp commentDate = new Timestamp(System.currentTimeMillis());
		boolean read = false;

		Comment comment = new Comment(getNewCommentId(), documentId, commentDate, employeeNumber, value.get("value"), read);
		commentRepository.save(comment);

		UserDetail userDetail = userDetailRepository.findOne(employeeNumber);
		return createCommentInfoForm(comment, userDetail);
	}
}
