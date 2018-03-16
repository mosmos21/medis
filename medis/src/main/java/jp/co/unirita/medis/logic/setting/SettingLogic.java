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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

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

	private static final String COMMENT_NOTIFICATION_TAG = "g0000000000";
	private final Path rootLocation = Paths.get("src/main/resources/image/");

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	UserDetailRepository userDetailRepository;

	public List<Tag> getMonitoringTag(String employeeNumber) {
		List<NotificationConfig> notifications = notificationConfigRepository
				.findByEmployeeNumberAndTagIdNot(employeeNumber, COMMENT_NOTIFICATION_TAG);
		return notifications.stream()
                .map(NotificationConfig::getTagId)
                .map(tagRepository::findOne)
                .collect(Collectors.toList());
	}

	public List<NotificationsForm> getNotificationTag(String employeeNumber) {
		List<NotificationConfig> notifications = notificationConfigRepository
				.findByEmployeeNumberAndTagIdNot(employeeNumber, COMMENT_NOTIFICATION_TAG);
		List<NotificationsForm> forms = new ArrayList<>();
		for(NotificationConfig config: notifications) {
            Tag tag= tagRepository.findOne(config.getTagId());
            forms.add(new NotificationsForm(tag.getTagId(),tag.getTagName(),
                    config.isMailNotification(), config.isBrowserNotification()));
        }
		return forms;
	}

	public Map<String, Boolean> getNotificationComment(String employeeNumber) {
		NotificationConfig config =  notificationConfigRepository
                .findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG));
		Map<String, Boolean> setting = new HashMap<>();
		setting.put("browserNotification", config.isBrowserNotification());
		setting.put("mailNotification", config.isMailNotification());
		return setting;
	}

	public void updateUserDetail(String employeeNumber, UserDetail userDetail) {
		userDetail.setEmployeeNumber(employeeNumber);
		userDetailRepository.saveAndFlush(userDetail);
	}

	public void updateMonitoringTag(String employeeNumber, List<Tag> tags) {
		List<NotificationConfig> notification = new ArrayList<>();
		notification.add(notificationConfigRepository
                .findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG)));
		for(Tag tag: tags) {
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
		for (NotificationConfig conf : notification) {
			notificationConfigRepository.saveAndFlush(conf);
		}
	}

	public void updateNotificationTag(String employeeNumber, List<NotificationConfig> notifications) {
		notifications.forEach(n -> n.setEmployeeNumber(employeeNumber));
		notifications.forEach(notificationConfigRepository::save);
		notificationConfigRepository.flush();
	}


	public void updateNotificationComment(String employeeNumber, Map<String, Boolean> map) {
        NotificationConfig config =  notificationConfigRepository
                .findOne(new NotificationConfig.PK(employeeNumber, COMMENT_NOTIFICATION_TAG));
		config.setBrowserNotification(map.get("browserNotification"));
		config.setMailNotification(map.get("mailNotification"));
		notificationConfigRepository.saveAndFlush(config);
	}

	public void createNotificationComment(String employeeNumber, Map<String, Boolean> map) {
        NotificationConfig config =  new NotificationConfig();
        config.setEmployeeNumber(employeeNumber);
        config.setTagId(COMMENT_NOTIFICATION_TAG);
		config.setBrowserNotification(map.get("browserNotification"));
		config.setMailNotification(map.get("mailNotification"));
		notificationConfigRepository.saveAndFlush(config);
	}

	public void store(String employeeNumber, MultipartFile file) {
		try {
			Files.copy(file.getInputStream(), this.rootLocation.resolve(employeeNumber + ".png"));
		} catch (Exception e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public Resource loadFile(String filename) {
		try {
			Path file = rootLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("FAIL!");
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("FAIL!");
		}
	}

	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	public void init() {
		try {
			Files.createDirectory(rootLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage!");
		}
	}
}
