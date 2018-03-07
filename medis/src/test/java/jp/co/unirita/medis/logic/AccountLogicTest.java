package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfo;
import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.domain.user.UserRepository;
import jp.co.unirita.medis.logic.system.AccountLogic;
import jp.co.unirita.medis.util.exception.NotExistException;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/tempkey_info-delete.sql", "file:resources/sql/tempkey_info-insert.sql",
	"file:resources/sql/user_info-delete.sql", "file:resources/sql/user_info-insert.sql",
		"file:resources/sql/user_detail-delete.sql", "file:resources/sql/user_detail-insert.sql"})
public class AccountLogicTest {

    @Autowired
    AccountLogic accountLogic;
    @Autowired
    TempkeyInfoRepository tempkeyInfoRepository;
    @Autowired
    UserRepository userRepository;

    @Test
    public void パスワード再設定用のデータの確認結果の取得_存在する場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("test","medis.masa0@gmail.com");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "OK");
        assertEquals("パスワード再設定用のデータが確認できませんでした", testData , result);
    }

    @Test
    public void パスワード再設定用のデータの確認結果の取得_ユーザが存在しない場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("unknown","medis.masa0@gmail.com");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "NG");
        testData.put("message", "ユーザが見つかりませんでした");
        assertEquals("パスワード再設定用のデータの確認結果の取得（ユーザが存在しない場合）が正しく動作していません", testData , result);
    }

    @Test
    public void パスワード再設定用のデータの確認結果の取得_社員番号とメールアドレスの組み合わせが一致しない場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("test","unknown@gmail.com");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "NG");
        testData.put("message", "社員番号とメールアドレスの組み合わせが一致しません");
        assertEquals("パスワード再設定用のデータの確認結果の取得（社員番号とメールアドレスの組み合わせが一致しない場合）が正しく動作していません", testData , result);
    }

    @Test
    public void 一時キーの生成() {
        String result = accountLogic.issueTempKey("test");
        assertNotNull("一時キーの生成が正しく動作していません", result);
    }

    @Test
    public void 送信された一時キーの有効性の確認_存在する場合() {
    	TempkeyInfo column = tempkeyInfoRepository.findOne("gu");
    	column.setChangeDate(new Timestamp(System.currentTimeMillis()));
    	tempkeyInfoRepository.saveAndFlush(column);
        Map<String,String> result = accountLogic.checkTempKeyIntegrity("88169f9f17664d8982cd2a38da0b357e");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "OK");
        assertEquals("送信された一時キーの有効性の確認（存在する場合）が正しく動作していません", testData , result);
    }

    @Test
    public void 送信された一時キーの有効性の確認_存在しない場合() {
        Map<String,String> result = accountLogic.checkTempKeyIntegrity("00000000000000000000000000000000");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "NG");
        testData.put("message", "登録されていないキーを使用しています");
        assertEquals("送信された一時キーの有効性の確認（存在しない場合）が正しく動作していません", testData , result);
    }

    @Test
    public void 送信された一時キーの時間制限の確認() {
        Map<String,String> result = accountLogic.checkTempKeyIntegrity("ceb03b0dacdf4a9080259ff535fab825");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "NG");
        testData.put("message", "メール発行から30分を過ぎています");
        assertEquals("送信された一時キーの時間制限の確認が正しく動作していません", testData , result);
    }

    @Test
    public void パスワードの再設定_成功時() throws NotExistException {
        accountLogic.passwordReset("test","medis.masa0@gmail.com","newpass");
        String result = userRepository.findOne("test").getPassword();
        assertEquals("パスワードの再設定（成功時）が正しく動作していません", "newpass" , result);
    }

    @Test
    public void パスワードの再設定_ユーザが存在していない場合() {
    	String result = "";
        try {
			accountLogic.passwordReset("unkown","medis.masa0@gmail.com","newpass");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("パスワードの再設定（ユーザが存在していない場合）が正しく動作していません", "存在していない社員番号を参照しています" , result);
    }

    @Test
    public void パスワードの再設定_ユーザ名とメールアドレスの組み合わせが間違っている場合() {
    	String result = "";
        try {
			accountLogic.passwordReset("test","unknown@gmail.com","newpass");
		} catch (NotExistException e) {
			result = e.getMessage();
		}
        assertEquals("パスワードの再設定（ユーザ名とメールアドレスの組み合わせが間違っている場合）が正しく動作していません", "ユーザ名とメールアドレスの組み合わせが間違っています" , result);
    }
}
