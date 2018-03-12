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
@Sql({"document_info_monitoringlogictest-deletel", "document_info_monitoringlogictest-insert",
	  "notification_config_monitoringlogictest-delete","notification_config_monitoringlogictest-insert,",
	  "template_tag_monitoringlogictest-delete.sql","template_tag_monitoringlogictest-insert"})
public class InfomationLogicTest{
	@Autowired
	InfomationLogic infomationLogic;

	@Test
    public void adminが設定した監視タグのついたドキュメント一覧() {
        List<InfomationForm> documentInfoList = infomationLogic.getAllInfomationList("admin");
        assertEquals("adminの設定した監視タグのついたドキュメントが取得できませんでした", 2, documentInfoList.size());
    }

}
