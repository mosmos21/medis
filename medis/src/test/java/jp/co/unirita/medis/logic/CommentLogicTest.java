package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.comment.Comment;
import jp.co.unirita.medis.domain.comment.CommentRepository;
import jp.co.unirita.medis.form.document.CommentInfoForm;
import jp.co.unirita.medis.logic.document.CommentLogic;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/document_info-insert.sql", "file:resources/sql/comment-insert.sql",
		"file:resources/sql/user_detail-insert.sql" })
public class CommentLogicTest {

    @Autowired
    CommentLogic commentLogic;
    @Autowired
    CommentRepository commentRepository;

    @Test
    public void コメント情報の取得() {
    	List<CommentInfoForm> result = commentLogic.getCommentInfo("d0000000003");
        assertEquals("コメント情報の取得が正しく動作していません", 5, result.size());
    }

    @Test
    public void コメント既読判定() throws IdIssuanceUpperException {
    	commentLogic.alreadyRead("d0000000003","c0000000013");
    	boolean result = commentRepository.findOne("c0000000013").isRead();
        assertEquals("コメント既読判定が正しく動作していません", true, result);
    }

    @Test
    public void コメント保存() throws IdIssuanceUpperException {
    	Map<String,String> testValue = new HashMap<>();
    	testValue.put("value", "てすと");
    	CommentInfoForm result = commentLogic.save("d0000000003","g00000",testValue);

    	CommentInfoForm testData = new CommentInfoForm();
    	testData.setCommentDate(result.getCommentDate());
    	testData.setLastName("ユニリタ");
    	testData.setFirstName("太郎");
    	testData.setEmployeeNumber("g00000");
    	testData.setCommentId("o0000000017");
    	testData.setCommentContent("てすと");
        assertEquals("コメントの保存が正しく動作していません", testData, result);
    }
}
