package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.util.ArgumentCheckLogic;
import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.exception.NotExistException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/user_info-insert.sql",
		"file:resources/sql/update_info-insert.sql",
		"file:resources/sql/template_info-insert.sql",
		"file:resources/sql/document_info-insert.sql",
		"file:resources/sql/comment-insert.sql" })
public class ArgumentCheckLogicTest {

    @Autowired
    ArgumentCheckLogic argumentCheckLogic;

    @Test
    public void 権限のチェック_管理者の場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkAdminAuthority("medis");
		} catch (AuthorityException e) {
			result = e.getMessage();
		}
        assertEquals("権限のチェックが正しく動作していません（管理者の場合）", "", result);
    }

    @Test
    public void 権限のチェック_管理者以外の場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkAdminAuthority("gu");
		} catch (AuthorityException e) {
			result = e.getMessage();
		}
        assertEquals("権限のチェックが正しく動作していません（管理者以外の場合）", "User does not have administrator authority.", result);
    }


    @Test
    public void ユーザチェック_ユーザが存在する場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkUserExist("medis");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("ユーザチェック（ユーザが存在する場合）が正しく動作していません", "", result);
    }

    @Test
    public void ユーザチェック_ユーザが存在しない場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkUserExist("unknown");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("ユーザチェック（ユーザが存在しない場合）が正しく動作していません", "User does not exist.", result);
    }

    @Test
    public void コンテンツ権限チェック_権限がある場合() throws AuthorityException {
    	String result = "";
    	User user = new User();
    	user.setEmployeeNumber("medis");
        try {
			argumentCheckLogic.checkUser(user,"medis","contents");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("コンテンツ権限チェック（権限がある場合）が正しく動作していません", "", result);
    }

    @Test
    public void コンテンツ権限チェック_ユーザが存在しない場合() throws AuthorityException {
    	String result = "";
    	User user = new User();
    	user.setEmployeeNumber("medis");
        try {
			argumentCheckLogic.checkUser(user,"unknown","contents");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("コンテンツ権限チェック（ユーザが存在しない場合）が正しく動作していません", "存在しないユーザです", result);
    }

    @Test
    public void コンテンツ権限チェック_コンテンツ権限がない場合() throws NotExistException {
    	String result = "";
    	User user = new User();
    	user.setEmployeeNumber("test");
        try {
			argumentCheckLogic.checkUser(user,"medis","contents");
		} catch (AuthorityException e) {
			result = e.getMessage();
		}
        assertEquals("コンテンツ権限チェック（コンテンツ権限がない場合）が正しく動作していません", "他ユーザのcontentsは取得することができません", result);
    }

    @Test
    public void 更新IDチェック_IDが存在する場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkLastUpdateId("u0000000000");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("更新IDチェック（IDが存在する場合）が正しく動作していません", "", result);
    }

    @Test
    public void 更新IDチェック_IDが存在しない場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkLastUpdateId("u8888888888");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("更新IDチェック（IDが存在しない場合）が正しく動作していません", "存在しない更新IDです", result);
    }

    @Test
    public void テンプレートチェック_IDが存在する場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkTemplateId("t0000000000");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("テンプレートチェック（IDが存在する場合）が正しく動作していません", "", result);
    }

    @Test
    public void テンプレートチェック_IDが存在しない場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkTemplateId("t8888888888");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("テンプレートチェック（IDが存在しない場合）が正しく動作していません", "The templateId does not exist.", result);
    }

    @Test
    public void ドキュメントチェック_IDが存在する場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkDocumentId("d0000000000");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("ドキュメントチェック（IDが存在する場合）が正しく動作していません", "", result);
    }

    @Test
    public void ドキュメントチェック_IDが存在しない場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkDocumentId("d8888888888");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("ドキュメントチェック（IDが存在しない場合）が正しく動作していません", "存在しないドキュメントIDです", result);
    }

    @Test
    public void コメントチェック_IDが存在する場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkCommentId("c0000000000");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("コメントチェック（IDが存在する場合）が正しく動作していません", "", result);
    }

    @Test
    public void コメントチェック_IDが存在しない場合() {
    	String result = "";
        try {
			argumentCheckLogic.checkCommentId("c8888888888");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("コメントチェック（IDが存在しない場合）が正しく動作していません", "存在しないコメントIDです", result);
    }
}
