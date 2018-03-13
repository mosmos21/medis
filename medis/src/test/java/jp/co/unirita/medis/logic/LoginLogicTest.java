package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.form.util.UserLoginForm;
import jp.co.unirita.medis.logic.util.LoginLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/user_info-delete.sql", "file:resources/sql/user_info-insert.sql"})
public class LoginLogicTest {

    @Autowired
    LoginLogic loginLogic;

    @Test
    public void ログイン処理() {
    	UserLoginForm loginForm = new UserLoginForm();
    	loginForm.setEmployeeNumber("medis");
    	loginForm.setPassword("medis");
        loginLogic.login(loginForm);

    	Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	User testData = new User();
    	testData.setEmployeeNumber("medis");
    	testData.setAuthorityId("a0000000000");
    	testData.setEnabled(true);
    	testData.setPassword("medis");
        assertEquals("ログインができませんでした", testData, principal);
    }
}
