package jp.co.unirita.medis.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.toppage.ToppageRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.NotificationCommentForm;
import jp.co.unirita.medis.form.NotificationsForm;
import jp.co.unirita.medis.logic.setting.SettingLogic;

@RestController
@RequestMapping("/v1/settings")
public class SettingController {

	@Autowired
	ToppageRepository toppageRepository;
	@Autowired
	UserDetailRepository userdetailRepository;
	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	SettingLogic settingLogic;

	// ユーザ情報設定情報の取得
	@RequestMapping(value = "me", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<UserDetail> getUserDetail(@AuthenticationPrincipal User user) {
		List<UserDetail> info = userdetailRepository.findAllByEmployeeNumber(user.getEmployeeNumber());
		return info;
	}

	// ユーザ情報設定情報の更新
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "me", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateUserDetail(@AuthenticationPrincipal User user, @RequestBody @Valid UserDetail postData,
			HttpServletRequest request, HttpServletResponse response) {
		settingLogic.updateUserDetail(user, postData);
	}

	/*
	 * //トップページ設定情報の取得
	 *
	 * @RequestMapping(value = "me/toppage", method = RequestMethod.GET, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) List<Toppage>
	 * getToppageOrder(@AuthenticationPrincipal User user) { List<Toppage> info =
	 * toppageRepository.findAllByEmployeeNumberOrderByToppageOrderAsc(user.
	 * getEmployeeNumber()); return info; }
	 *
	 * //トップページ設定情報の更新
	 *
	 * @ResponseStatus(HttpStatus.OK)
	 *
	 * @RequestMapping(value = "me/toppage", method = RequestMethod.POST, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) void
	 * updateToppageOrder(@AuthenticationPrincipal User user, @RequestBody @Valid
	 * List<Toppage> postData, HttpServletRequest request, HttpServletResponse
	 * response) { settingLogic.updateToppageOrder(user,postData); }
	 */
	// 監視タグ設定情報の取得
	@RequestMapping(value = "me/monitoring_tags", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<Tag> getMonitoringTags(@AuthenticationPrincipal User user) {
		List<Tag> infoList = null;
		infoList = settingLogic.getMonitoringTags(user);
		return infoList;
	}

	// 監視タグ設定情報の更新
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "me/monitoring_tags", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateMonitoring_Tags(@AuthenticationPrincipal User user,
			@RequestBody @Valid List<NotificationConfig> postData, HttpServletRequest request,
			HttpServletResponse response) {
		settingLogic.updateMonitoringTags(user, postData);
	}

	// 通知設定情報の取得(タグ)
	@RequestMapping(value = "me/tag_notifications", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<NotificationsForm> getNotificationTag(@AuthenticationPrincipal User user) {
		List<NotificationsForm> infoList = null;
		infoList = settingLogic.getNotificationTag(user);
		return infoList;
	}

	// 通知設定情報の更新(タグ)
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = "me/tag_notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	void updateNotificationTag(@AuthenticationPrincipal User user,
			@RequestBody @Valid List<NotificationConfig> postData, HttpServletRequest request,
			HttpServletResponse response) {
		settingLogic.updateNotificationTag(user, postData);
	}

	// 通知設定情報の取得(コメント)
	@RequestMapping(value = "me/comment_notifications", method = RequestMethod.GET, consumes = MediaType.APPLICATION_JSON_VALUE)
	List<NotificationCommentForm> getNotificationComment(@AuthenticationPrincipal User user) {
		List<NotificationCommentForm> infoList = null;
		infoList = settingLogic.getNotificationCommentInfo(user);
		System.out.println("AAAA"+infoList);
		return infoList;
	}

	// 通知設定情報の更新(コメント)
		@ResponseStatus(HttpStatus.OK)
		@RequestMapping(value = "me/comment_notifications", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		void updateNotificationComment(@AuthenticationPrincipal User user,
				@RequestBody @Valid  NotificationCommentForm postData, HttpServletRequest request,
				HttpServletResponse response) {
			settingLogic.updateNotificationComment(user, postData);
		}
}