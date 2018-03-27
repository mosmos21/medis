package jp.co.unirita.medis.logic;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.setting.NotificationsForm;
import jp.co.unirita.medis.logic.setting.SettingLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
@Sql({ "file:resources/sql/notification_config_monitoring_logic_test-insert.sql",
	   "file:resources/sql/tag-insert.sql",
	   "file:resources/sql/user_detail_monitoring_logic_test-insert.sql"})
public class SettingLogicTest {

	@Autowired
	SettingLogic settingLogic;
	@Autowired
	UserDetailRepository userDetailRepository;

	@Test
	public void g00001の監視タグ情報の一覧を取得する() {
		List<Tag> notifications = settingLogic.getMonitoringTag("g00002");
		assertEquals("ユーザg00001のお気に入りが取得できませんでした", 2, notifications.size());
	}

	@Test
	public void g0003が監視タグを設定していない場合の情報の一覧を取得する() {
		List<Tag> notifications = settingLogic.getMonitoringTag("g00003");
		assertEquals("ユーザg00001のお気に入りが取得できませんでした", 0, notifications.size());
	}

	@Test
	public void g00002の監視しているタグの通知設定を取得する() {
		List<NotificationsForm> forms = settingLogic.getNotificationTag("g00002");
		assertEquals("ユーザmedisのお気に入りが取得できませんでした", 2, forms.size());
	}

	@Test
	public void g00003の監視しているタグがない場合の通知設定を取得する() {
		List<NotificationsForm> forms = settingLogic.getNotificationTag("g00003");
		assertEquals("ユーザmedisのお気に入りが取得できませんでした", 0, forms.size());
	}

	@Test
	public void adminのコメントに対する通知設定の情報を取得する() {
		Map<String, Boolean> setting = settingLogic.getNotificationComment("admin");
		assertEquals("ユーザmedisのお気に入りが取得できませんでした", 2, setting.size());
	}

	@Test
	public void ユーザ情報の更新() {
		UserDetail userDetail1 =new UserDetail();
		userDetail1.setEmployeeNumber("97970");
		userDetail1.setLastName("hoge");
		userDetail1.setFirstName("fuga");
		userDetail1.setLastNamePhonetic("ホゲ");
		userDetail1.setFirstNamePhonetic("フガ");
		userDetail1.setMailaddress("hoge@fuga.co.jp");
		userDetail1.setIcon(false);

		UserDetail userDetail2 =new UserDetail();
		userDetail2.setEmployeeNumber("97970");
		userDetail2.setLastName("hoge");
		userDetail2.setFirstName("fuga");
		userDetail2.setLastNamePhonetic("ホゲ");
		userDetail2.setFirstNamePhonetic("フガ");
		userDetail2.setMailaddress("hoge@fuga.co.jp");
		userDetail2.setIcon(false);

		settingLogic.updateUserDetail("97970",userDetail2);


		assertEquals("ユーザhogeのユーザ情報の更新ができませんでした", userDetail1,userDetailRepository.findOne("97970"));
	}
}
