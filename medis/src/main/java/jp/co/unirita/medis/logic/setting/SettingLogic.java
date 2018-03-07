package jp.co.unirita.medis.logic.setting;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.setting.NotificationsForm;

@Service
public class SettingLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	public List<Tag> getMonitoringTags(String employeeNumber) {
		List<NotificationConfig> notifications = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		return notifications.stream()
                .map(NotificationConfig::getTagId)
                .map(tagRepository::findOne)
                .collect(Collectors.toList());
	}

	public List<NotificationsForm> getNotificationTag(String employeeNumber) {
		List<NotificationConfig> notifications = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		List<NotificationsForm> forms = new ArrayList<>();

		for(NotificationConfig config: notifications) {
            Tag tag= tagRepository.findOne(config.getTagId());
            forms.add(new NotificationsForm(tag.getTagId(),tag.getTagName(),
                    config.isMailNotification(), config.isBrowserNotification()));
        }
		return forms;
	}

	public void updateUserDetail(String employeeNumber, UserDetail userDetail) {
		userDetail.setEmployeeNumber(employeeNumber);
		userDetailRepository.saveAndFlush(userDetail);
	}

	public void updateMonitoringTags(String employeeNumber, List<NotificationConfig> notifications) {
		for(NotificationConfig config: notifications) {
            NotificationConfig ref = notificationConfigRepository
                    .findOne(new NotificationConfig.PK(employeeNumber, config.getTagId()));
            config.setEmployeeNumber(employeeNumber);
            if (ref != null) {
                config.setMailNotification(ref.isMailNotification());
                config.setBrowserNotification(ref.isBrowserNotification());
            }
        }
		List<NotificationConfig> del = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
		notificationConfigRepository.delete(del);
		notifications.forEach(n -> n.setEmployeeNumber(employeeNumber));
		notifications.forEach(notificationConfigRepository::save);
        notificationConfigRepository.flush();
	}

	public void updateNotificationTag(String employeeNumber, List<NotificationConfig> notifications) {
		notifications.forEach(n -> n.setEmployeeNumber(employeeNumber));
		notifications.forEach(notificationConfigRepository::save);
		notificationConfigRepository.flush();
	}

	public Map<String, Boolean> getNotificationCommentInfo(String employeeNumber) {
		NotificationConfig config =  notificationConfigRepository
                .findOne(new NotificationConfig.PK(employeeNumber, "g0000000000"));
		Map<String, Boolean> setting = new HashMap<>();
		setting.put("browserNotification", config.isBrowserNotification());
		setting.put("mailNotification", config.isMailNotification());
		return setting;
	}

	public void updateNotificationComment(String employeeNumber, Map<String, Boolean> map) {
        NotificationConfig config =  notificationConfigRepository
                .findOne(new NotificationConfig.PK(employeeNumber, "g0000000000"));
		config.setBrowserNotification(map.get("browserNotification"));
		config.setMailNotification(map.get("mailNotification"));
		notificationConfigRepository.saveAndFlush(config);
	}
}
