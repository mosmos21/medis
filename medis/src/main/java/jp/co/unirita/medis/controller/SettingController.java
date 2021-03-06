package jp.co.unirita.medis.controller;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.form.setting.NotificationsForm;
import jp.co.unirita.medis.logic.setting.SettingLogic;
import jp.co.unirita.medis.logic.system.AccountLogic;

@RestController
@RequestMapping("/v1/settings")
public class SettingController {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	AccountLogic accountLogic;
	@Autowired
	SettingLogic settingLogic;
	@Autowired
	NotificationConfigRepository notificationConfigRepository;

	/**
	 * ログインしてるユーザの詳細情報を取得する
	 * @param user ログインしているユーザ
	 * @return ユーザ詳細情報(@see jp.co.unirita.medis.domain.userdetail.UserDetail)
	 */
	@GetMapping(value = "me")
	@ResponseStatus(HttpStatus.OK)
	public UserDetail getUserDetail(@AuthenticationPrincipal User user) {
		return accountLogic.getUserDetail(user.getEmployeeNumber());
	}

    /**
     * 監視タグ情報の一覧を取得する
     * @param user ログインしているユーザ
     * @return 監視しているタグ(@see jp.co.unirita.medis.domain.tag.Tag)のリスト
     */
	@GetMapping(value = "me/monitoring_tags")
    @ResponseStatus(HttpStatus.OK)
	public List<Tag> getMonitoringTag(@AuthenticationPrincipal User user) {
		return settingLogic.getMonitoringTag(user.getEmployeeNumber());
	}


    /**
     * 監視しているタグの通知設定
     * @param user ログインしているユーザ
     * @return 監視しているタグの通知設定(@see jp.co.unirita.medis.form.NotificationsForm)のリスト
     */
    @GetMapping(value = "me/tag_notifications")
    @ResponseStatus(HttpStatus.OK)
    List<NotificationsForm> getNotificationTag(@AuthenticationPrincipal User user) {
        return settingLogic.getNotificationTag(user.getEmployeeNumber());
    }


    /**
     * コメントに対する通知設定の情報を取得する
     * @param user ログインしているユーザ
     * @return メール通知とブラウザ通知の設定情報
     */
    @GetMapping(value = "me/comment_notifications")
    @ResponseStatus(HttpStatus.OK)
    public Map<String, Boolean> getNotificationComment(@AuthenticationPrincipal User user) {
        return settingLogic.getNotificationComment(user.getEmployeeNumber());
    }


    /**
     * ユーザ情報を更新する
     * @param user ログインしているユーザ
     * @param data 新しいユーザ情報
     */
	@PostMapping(value = "me")
    @ResponseStatus(HttpStatus.CREATED)
	void updateUserDetail(@AuthenticationPrincipal User user, @RequestBody @Valid UserDetail data) {
		settingLogic.updateUserDetail(user.getEmployeeNumber(), data);
	}


    /**
     * 監視するタグの情報を更新する
     * @param user ログインしているユーザ
     * @param data　更新した監視タグ(@see jp.co.unirita.medis.domain.notification.NotificationConfig)のリスト
     */
	@PostMapping(value = "me/monitoring_tags")
    @ResponseStatus(HttpStatus.CREATED)
	void updateMonitoringTag(@AuthenticationPrincipal User user, @RequestBody List<Tag> tags) {
		settingLogic.updateMonitoringTag(user.getEmployeeNumber(), tags);
	}


    /**
     *監視しているタグの通知設定を更新する
     * @param user ログインしているユーザ
     * @param data　監視タグのそれぞれのメール通知フラグとブラウザ通知フラグ(@see jp.co.unirita.medis.form.NotificationsForm)のリスト
     */
	@PostMapping(value = "me/tag_notifications")
    @ResponseStatus(HttpStatus.CREATED)
	void updateNotificationTag(@AuthenticationPrincipal User user, @RequestBody @Valid List<NotificationConfig> data) {
		settingLogic.updateNotificationTag(user.getEmployeeNumber(), data);
	}


    /**
     * コメントに対する通知の設定を行う
     * @param user ログインしているユーザ
     * @param data メール通知フラグとブラウザ通知フラグ
     */
    @PostMapping(value = "me/comment_notifications")
    @ResponseStatus(HttpStatus.CREATED)
    void updateNotificationComment(@AuthenticationPrincipal User user, @RequestBody Map<String, Boolean> data) {
        settingLogic.updateNotificationComment(user.getEmployeeNumber(), data);
    }
}