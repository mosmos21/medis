package jp.co.unirita.medis.logic.system;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.tempkeyInfo.TempkeyInfoRepository;
import jp.co.unirita.medis.logic.system.AccountLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/authority-delete.sql", "file:resources/sql/authority-insert.sql",
		"file:resources/sql/user_info-delete.sql", "file:resources/sql/user_info-insert.sql",
		"file:resources/sql/user_detail-delete.sql", "file:resources/sql/user_detail-insert.sql"})
public class AccountLogicTest {

    @Autowired
    AccountLogic accountLogic;
    @Autowired
    TempkeyInfoRepository tempkeyInfoRepository;

    @Test
    public void パスワード再設定用のデータの確認結果の取得() {
        //TempkeyInfo tempkeyInfo = tempkeyInfoRepository.findByTempKey("test");
        Map<String,String> result = accountLogic.checkUserIntegrity("test","medis.masa0@gmail.com");
        Map<String,String> testData = new HashMap<>();
        testData.put("result", "OK");
        assertEquals("パスワード再設定用のデータが確認できませんでした", testData , result);
    }
}
