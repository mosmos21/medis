package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.logic.system.UserLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/user_info-insert.sql"})
public class UserLogicTest {

    @Autowired
    UserLogic userLogic;

    @Test
    public void ユーザのロード() {
    	User testData = new User();
    	testData.setEmployeeNumber("medis");
    	testData.setAuthorityId("a0000000000");
    	testData.setEnabled(true);
    	testData.setPassword("medis");

        User user = userLogic.loadUserByUsername("medis");
        assertEquals("ユーザのロードができませんでした", testData, user);
    }
}
