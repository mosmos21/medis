package jp.co.unirita.medis.logic;


import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.form.document.DocumentInfoForm;
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
    public void adminがお気に入り登録したドキュメント() {
        List<DocumentInfoForm> documentInfoList = monitoringLogic.getMonitoringList("admin");
        assertEquals("お気に入りが取得できませんでした", 2, documentInfoList.size());
    }

	@Test
    public void g00000がお気に入り登録したドキュメント() {
        List<DocumentInfoForm> documentInfoList = monitoringLogic.getMonitoringList("g00000");
        assertEquals("お気に入りが取得できませんでした", 1, documentInfoList.size());
    }

}
