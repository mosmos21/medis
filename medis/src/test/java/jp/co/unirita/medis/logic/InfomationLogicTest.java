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
@Sql({ "file:resources/sql/document_info_monitoring_logic_test-insert.sql",
		"file:resources/sql/notification_config_monitoring_logic_test-insert.sql",
		"file:resources/sql/template_tag_monitoring_logic_test-insert.sql",
		"file:resources/sql/update_info_monitoring_logic_test-insert.sql" })
public class InfomationLogicTest {
	@Autowired
	InfomationLogic infomationLogic;

	@Test
	public void UpdateIDがない場合のg00001のトップページの更新情報() {
		List<InfomationForm> documentInfoList = infomationLogic.getAllInfomationList("g00001");
		assertEquals("g00001のトップページ更新情報を取得できませんでした", 2, documentInfoList.size());
	}

	@Test
	public void UpdateIDがu0000000000以降のg00001のトップページの更新情報() {
		List<InfomationForm> documentInfoList = infomationLogic.getInfomationList("g00001", "u0000000000");
		assertEquals("g00001のUpdateID:u0000000000以降のトップページ更新情報を取得できませんでした", 2, documentInfoList.size());
	}

	@Test
	public void UpdateIDがu0000000010以降のg00001のトップページの更新情報() {
		List<InfomationForm> documentInfoList = infomationLogic.getInfomationList("g00001", "u0000000010");
		assertEquals("g00001のUpdateID:u0000000010以降のトップページ更新情報を取得できませんでした", 1, documentInfoList.size());
	}

	@Test
	public void UpdateIDがu0000000015以降のg00001のトップページの更新情報() {
		List<InfomationForm> documentInfoList = infomationLogic.getInfomationList("g00001", "u0000000015");
		assertEquals("g00001のUpdateID:u0000000015以降のトップページ更新情報を取得できませんでした", 0, documentInfoList.size());
	}

}
