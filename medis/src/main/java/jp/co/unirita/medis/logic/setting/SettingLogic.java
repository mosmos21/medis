package jp.co.unirita.medis.logic.setting;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import jp.co.unirita.medis.config.path.ServerResourcesPathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.setting.NotificationsForm;
import jp.co.unirita.medis.util.exception.DBException;

@Service
@Transactional(rollbackFor = Exception.class)
public class SettingLogic {

	private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final String COMMENT_NOTIFICATION_TAG = "g0000000000";

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	ServerResourcesPathUtil serverResourcesPathUtil;

	public List<Tag> getMonitoringTag(String employeeNumber) {
		try {
			List<NotificationConfig> notifications = notificationConfigRepository
					.findByEmployeeNumberAndTagIdNot(employeeNumber, COMMENT_NOTIFICATION_TAG);
			return notifications.stream()
					.map(NotificationConfig::getTagId)
					.map(tagRepository::findOne)
					.collect(Collectors.toList());
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: getMonitoringTag]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: getMonitoringTag]");
		}
	}

	public List<NotificationsForm> getNotificationTag(String employeeNumber) {
		try {
			List<NotificationConfig> notifications = notificationConfigRepository
					.findByEmployeeNumberAndTagIdNot(employeeNumber, COMMENT_NOTIFICATION_TAG);
			List<NotificationsForm> forms = new ArrayList<>();
			for (NotificationConfig config : notifications) {
				Tag tag = tagRepository.findOne(config.getTagId());
				forms.add(new NotificationsForm(tag.getTagId(), tag.getTagName(),
						config.isMailNotification(), config.isBrowserNotification()));
			}
			return forms;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: getNotificationTag]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: getNotificationTag]");
		}
	}

	public Map<String, Boolean> getNotificationComment(String employeeNumber) {
		try {
			NotificationConfig config = notificationConfigRepository
					.findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG));
			Map<String, Boolean> setting = new HashMap<>();
			setting.put("browserNotification", config.isBrowserNotification());
			setting.put("mailNotification", config.isMailNotification());
			return setting;
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: getNotificationComment]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: getNotificationComment]");
		}
	}

	public void updateUserDetail(String employeeNumber, UserDetail userDetail) {
		try {
			userDetail.setEmployeeNumber(employeeNumber);
			userDetailRepository.saveAndFlush(userDetail);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: updateUserDetail]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: updateUserDetail]");
		}
	}

	public void updateMonitoringTag(String employeeNumber, List<Tag> tags) {
		try {
			List<NotificationConfig> notification = new ArrayList<>();
			notification.add(notificationConfigRepository
					.findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG)));
			for (Tag tag : tags) {
				NotificationConfig config = notificationConfigRepository
						.findOne(new NotificationConfig.PK(employeeNumber, tag.getTagId()));
				if (config == null) {
					NotificationConfig ref = new NotificationConfig();
					ref.setEmployeeNumber(employeeNumber);
					ref.setTagId(tag.getTagId());
					ref.setMailNotification(true);
					ref.setBrowserNotification(true);
					notification.add(ref);
				} else {
					notification.add(config);
				}
			}
			List<NotificationConfig> del = notificationConfigRepository.findByEmployeeNumber(employeeNumber);
			notificationConfigRepository.delete(del);
			notificationConfigRepository.flush();
			for (NotificationConfig conf : notification) {
				notificationConfigRepository.saveAndFlush(conf);
			}
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: updateMonitoringTag]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: updateMonitoringTag]");
		}
	}

	public void updateNotificationTag(String employeeNumber, List<NotificationConfig> notifications) {
		try {
			notifications.forEach(n -> n.setEmployeeNumber(employeeNumber));
			notifications.forEach(notificationConfigRepository::save);
			notificationConfigRepository.flush();
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: updateNotificationTag]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: updateNotificationTag]");
		}
	}

	public void updateNotificationComment(String employeeNumber, Map<String, Boolean> map) {
		try {
			NotificationConfig config = notificationConfigRepository
					.findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG));
			config.setBrowserNotification(map.get("browserNotification"));
			config.setMailNotification(map.get("mailNotification"));
			notificationConfigRepository.saveAndFlush(config);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: updateNotificationComment]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: updateNotificationComment]");
		}
	}

	public void createNotificationComment(String employeeNumber, Map<String, Boolean> map) {
		try {
			NotificationConfig config = new NotificationConfig();
			config.setEmployeeNumber(employeeNumber);
			config.setTagId(COMMENT_NOTIFICATION_TAG);
			config.setBrowserNotification(map.get("browserNotification"));
			config.setMailNotification(map.get("mailNotification"));
			notificationConfigRepository.saveAndFlush(config);
		} catch (DBException e) {
			logger.error("DB Runtime Error[class: SettingLogic, method: createNotificationComment]");
			throw new DBException("DB Runtime Error[class: SettingLogic, method: createNotificationComment]");
		}
	}

	public void store(String employeeNumber, MultipartFile file) {
		Path rootLocation = Paths.get(serverResourcesPathUtil.getPath() +"/image/");
		try {
			if (Files.exists(rootLocation.resolve(employeeNumber + ".png"))) {
				Files.delete(rootLocation.resolve(employeeNumber + ".png"));
			}
			Files.copy(file.getInputStream(), rootLocation.resolve(employeeNumber + ".png"));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource loadFile(String filename) {
		Path rootLocation = Paths.get(serverResourcesPathUtil.getPath() +"/image/");
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() && resource.isReadable()) {
				return resource;
			} else {
				return new UrlResource(Paths.get(serverResourcesPathUtil.getPath() + "/default.png").toUri());
			}
		} catch (MalformedURLException e) {
			logger.error("error in loadFile()", e);
			throw new RuntimeException("FAIL!");
			// TODO 後で考える
		}
	}

	public void deleteAll() {
		Path rootLocation = Paths.get(serverResourcesPathUtil.getPath() +"/image/");
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		Path rootLocation = Paths.get(serverResourcesPathUtil.getPath() +"/image/");
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
