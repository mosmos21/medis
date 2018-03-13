package jp.co.unirita.medis.logic;


import static org.junit.Assert.*;

import java.util.List;

import jp.co.unirita.medis.form.document.DocumentInfoForm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.logic.setting.MonitoringLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({"document_info_monitoringlogictest-deletel", "document_info_monitoringlogictest-insert",
	  "notification_config_monitoringlogictest-delete","notification_config_monitoringlogictest-insert,",
	  "template_tag_monitoringlogictest-delete.sql","template_tag_monitoringlogictest-insert"})
public class MonitoringLogicTest {

	@Autowired
	MonitoringLogic monitoringLogic;

	@Test
    public void adminが設定した監視タグのついたドキュメント一覧() {
        List<DocumentInfoForm> documentInfoList = monitoringLogic.getMonitoringList("admin");
        assertEquals("adminの設定した監視タグのついたドキュメントが取得できませんでした", 2, documentInfoList.size());
    }

	@Test
    public void g00000が設定した監視タグのついたドキュメント一覧() {
        List<DocumentInfoForm> documentInfoList = monitoringLogic.getMonitoringList("g00000");
        assertEquals("g00000の設定した監視タグのついたドキュメントが取得できませんでした", 1, documentInfoList.size());
    }

	@Test
    public void 監視タグを設定していない場合() {
        List<DocumentInfoForm> documentInfoList = monitoringLogic.getMonitoringList("user");
        assertEquals("userはまだ監視タグを設定していません", 0, documentInfoList.size());
    }

}
