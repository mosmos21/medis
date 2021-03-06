package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.authority.Authority;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.system.UserManagementForm;
import jp.co.unirita.medis.logic.system.UserManagementLogic;
import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/authority-insert.sql",
		"file:resources/sql/user_info-insert.sql",
		"file:resources/sql/user_detail-insert.sql"})
public class UserManagementLogicTest {

    @Autowired
    UserManagementLogic userManagementLogic;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserDetailRepository userDetailRepository;

    @Test
    public void 全てのユーザ権限種別の取得() {
        List<Authority> authorityList = userManagementLogic.getAuthorityTypeList();
        assertEquals("ユーザ権限種別が全件取得できませんでした", 3, authorityList.size());
    }

    @Test
    public void 全てのユーザ情報詳細の一覧取得() {
        List<UserManagementForm> userManagementFormList = userManagementLogic.getUserManagement();
        assertEquals("ユーザ情報が全件取得できませんでした", 12, userManagementFormList.size());
    }

    @Test
    public void ユーザの有効_無効化_ユーザ権限の更新() {
    	User user = new User();
    	user.setEmployeeNumber("medis");
    	user.setAuthorityId("a0000000001");
    	user.setEnabled(false);
    	user.setPassword("medis");
        userManagementLogic.updateUserManagement("medis", false, "a0000000001");
        assertEquals("ユーザ情報が更新できませんでした"
        		, user, userRepository.findOne("medis"));
    }

    @Test
    public void 新規ユーザの作成_成功時() throws ConflictException, IdIssuanceUpperException {
    	UserManagementForm userManagementForm = new UserManagementForm();
    	userManagementForm.setEmployeeNumber("new");
    	userManagementForm.setLastName("last");
    	userManagementForm.setFirstName("first");
    	userManagementForm.setLastNamePhonetic("lastPhone");
    	userManagementForm.setFirstNamePhonetic("firstPhone");
    	userManagementForm.setMailaddress("new@hoge.jp");
    	userManagementForm.setIcon(true);
    	userManagementForm.setAuthorityId("a0000000001");
    	userManagementForm.setEnabled(true);
        userManagementLogic.createUser(userManagementForm);

    	UserDetail userDetail = new UserDetail();
    	userDetail.setEmployeeNumber("new");
    	userDetail.setLastName("last");
    	userDetail.setFirstName("first");
    	userDetail.setLastNamePhonetic("lastPhone");
    	userDetail.setFirstNamePhonetic("firstPhone");
    	userDetail.setMailaddress("new@hoge.jp");
    	userDetail.setIcon(true);
        assertEquals("ユーザ情報が新規作成できませんでした（userDetail側）"
        		+ "でした", userDetail, userDetailRepository.findOne("new"));


    	User user = new User();
    	user.setEmployeeNumber("new");
    	user.setAuthorityId("a0000000001");
    	user.setEnabled(true);
    	user.setPassword("88169f9f17664d8982cd2a38da0b357e");
        assertEquals("ユーザ情報が新規作成できませんでした（user側）"
        		+ "でした", user, userRepository.findOne("new"));
    }

    @Test
    public void 新規ユーザの作成_ユーザ名重複時() throws IdIssuanceUpperException {
    	String result = "";
    	UserManagementForm userManagementForm = new UserManagementForm();
    	userManagementForm.setEmployeeNumber("test");
    	userManagementForm.setLastName("last");
    	userManagementForm.setFirstName("first");
    	userManagementForm.setLastNamePhonetic("lastPhone");
		userManagementForm.setFirstNamePhonetic("firstPhone");
		userManagementForm.setMailaddress("new@hoge.jp");
		userManagementForm.setIcon(true);
		userManagementForm.setAuthorityId("a0000000001");
		userManagementForm.setEnabled(true);
		try {
			userManagementLogic.createUser(userManagementForm);
		} catch (ConflictException e) {
			result = e.getMessage();
		}
		assertEquals("新規ユーザ作成時にユーザ名が重複した際の処理が正しく動作していません", "emoloyee number must be unique. employeeNumber = test",
				result);
	}
}
