package jp.co.unirita.medis.logic;
import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.form.setting.InfomationForm;
import jp.co.unirita.medis.logic.setting.InfomationLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"file:resources/sql/document_info_monitoring_logic_test-insert.sql",
		"file:resources/sql/notification_config_monitoring_logic_test-insert.sql",
		"file:resources/sql/template_tag_monitoring_logic_test-insert.sql",
		"file:resources/sql/update_info_monitoring_logic_test-insert.sql"})
public class InfomationLogicTest{
	@Autowired
	InfomationLogic infomationLogic;

	@Test
    public void adminが設定した監視タグのついたドキュメント一覧() {
        List<InfomationForm> documentInfoList = infomationLogic.getAllInfomationList("admin");
        assertEquals("adminの設定した監視タグのついたドキュメントが取得できませんでした", 2, documentInfoList.size());
    }

}
