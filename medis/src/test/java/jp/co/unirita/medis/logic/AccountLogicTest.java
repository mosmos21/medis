package jp.co.unirita.medis.logic;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfo;
import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.form.util.UserLoginForm;
import jp.co.unirita.medis.logic.system.AccountLogic;
import jp.co.unirita.medis.logic.util.LoginLogic;
import jp.co.unirita.medis.util.exception.NotExistException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Timestamp;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountLogicTest {

    @Autowired
    AccountLogic accountLogic;
    @Autowired
    LoginLogic loginLogic;
    @Autowired
    TempkeyInfoRepository tempkeyInfoRepository;

    @Before
    @After
    @Sql("file:resources/sql/data.sql")
    public void setup() {
    }

    @Test
    public void パスワード再設定用のデータの確認結果の取得_存在する場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("g00000","medis.masa0@gmail.com");
        assertEquals("パスワード再設定用のデータが確認できませんでした", result.get("result"), "OK");
    }

    @Test
    public void パスワード再設定用のデータの確認結果の取得_ユーザが存在しない場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("unknown","medis.masa0@gmail.com");
        assertEquals("不明なユーザが参照されています", result.get("result"), "NG");
        assertEquals("エラーメッセージが間違っています", result.get("message"), "入力された社員番号が存在しません");
    }

    @Test
    public void パスワード再設定用のデータの確認結果の取得_社員番号とメールアドレスの組み合わせが一致しない場合() {
        Map<String,String> result = accountLogic.checkUserIntegrity("g00000","unknown@gmail.com");
        assertEquals("不明なユーザが参照されています", result.get("result"), "NG");
        assertEquals("エラーメッセージが間違っています", result.get("message"), "社員番号とメールアドレスの組み合わせが一致しません");
    }

    @Test
    public void 一時キーの生成() {
        String result = accountLogic.issueTempKey("g00000");
        assertNotNull("一時キーの生成が正しく動作していません", result);
    }

    @Test
    public void 送信された一時キーの有効性の確認_存在する場合() {
        String key = accountLogic.issueTempKey("g00000");
        Map<String, String> result = accountLogic.checkTempKeyIntegrity(key);
        assertEquals("キーチェックに失敗しています", result.get("result"), "OK");
        assertEquals("取得された社員番号が間違っています", result.get("employeeNumber"), "g00000");
        assertEquals("取得されたメールアドレスが間違っています", result.get("mailaddress"), "medis.masa0@gmail.com");
    }

    @Test
    public void 送信された一時キーの有効性の確認_存在しない場合() {
        Map<String,String> result = accountLogic.checkTempKeyIntegrity("DUMMY_TEMP_KEY");
        assertEquals("不明なキーが参照されています", result.get("result") , "NG");
        assertEquals("エラー文が間違っています", result.get("message"), "登録されていないキーを使用しています");
    }

    @Test
    public void 送信された一時キーの時間制限の確認() {
        String key = accountLogic.issueTempKey("g00000");
        TempkeyInfo info = tempkeyInfoRepository.findOne("g00000");
        info.setChangeDate(new Timestamp(0));
        tempkeyInfoRepository.saveAndFlush(info);
        Map<String, String> result = accountLogic.checkTempKeyIntegrity(key);
        assertEquals("一時キーの時間制限が動作していません", result.get("result"), "NG");
        assertEquals("エラー文が間違っています", result.get("message"), "メール発行から30分を過ぎています");
    }

    @Test
    public void パスワードの再設定_成功時() throws NotExistException {
        accountLogic.passwordReset("g00000","medis.masa0@gmail.com","newpass");
        String result = loginLogic.login(new UserLoginForm("g00000", "newpass")).getEmployeeNumber();
        assertEquals("パスワードの再設定が正しく動作していません", "g00000" , result);
    }

    @Test
    public void パスワードの再設定_ユーザが存在していない場合() {
        try {
            accountLogic.passwordReset("unkown","medis.masa0@gmail.com","newpass");
            fail("存在しないユーザのパスワードを設定しています");
        } catch (NotExistException e) {
            String result = e.getMessage();
            assertEquals("エラー文が間違っています", "存在していない社員番号を参照しています" , result);
        }
    }

    @Test
    public void パスワードの再設定_ユーザ名とメールアドレスの組み合わせが間違っている場合() {
        try {
            accountLogic.passwordReset("g00000","unknown@gmail.com","newpass");
            fail("存在しない社員番号とメールアドレスの組み合わせのユーザのパスワードを設定しています");
        } catch (NotExistException e) {
            String result = e.getMessage();
            assertEquals("エラー文が間違っています", "ユーザ名とメールアドレスの組み合わせが間違っています" , result);
        }
    }
}