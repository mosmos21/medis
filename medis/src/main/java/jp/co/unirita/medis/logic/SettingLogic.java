package jp.co.unirita.medis.logic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import jp.co.unirita.medis.domain.notificationconfig.NotificationConfig;
import jp.co.unirita.medis.domain.notificationconfig.NotificationConfigRepository;
import jp.co.unirita.medis.domain.tag.Tag;
import jp.co.unirita.medis.domain.tag.TagRepository;
import jp.co.unirita.medis.domain.toppage.Toppage;
import jp.co.unirita.medis.domain.toppage.ToppageRepository;
import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import jp.co.unirita.medis.domain.userdetail.UserDetailRepository;
import jp.co.unirita.medis.form.NotificationsForm;

@Service
public class SettingLogic {

	@Autowired
	NotificationConfigRepository notificationConfigRepository;
	@Autowired
	TagRepository tagRepository;
	@Autowired
	UserDetailRepository userDetailRepository;
	@Autowired
	ToppageRepository toppageRepository;

	public List<Tag> getMonitoringTags(User user) {
		List<NotificationConfig> notification = notificationConfigRepository.findByEmployeeNumber(user.getEmployeeNumber());
		Tag info = null;
		List<Tag> infoList = new ArrayList<>();
		for(int i=0; i < notification.size(); i++){
			info = new Tag();
			info = tagRepository.findByTagId(notification.get(i).getTagId());
			infoList.add(info);
		}
		return infoList;

	}

	public List<NotificationsForm> getNotification(User user) {
		List<NotificationConfig> notification = notificationConfigRepository.findByEmployeeNumber(user.getEmployeeNumber());
		Tag tag = new Tag();
		NotificationsForm info = null;
		List<NotificationsForm> infoList = new ArrayList<>();
		for(int i=0; i < notification.size(); i++){
			info = new NotificationsForm();
			tag = tagRepository.findByTagId(notification.get(i).getTagId());
			info.setTagId(tag.getTagId());
			info.setTagName(tag.getTagName());
			info.setMailNotification(notification.get(i).isMailNotification());
			info.setBrowserNotification(notification.get(i).isBrowserNotification());
			infoList.add(info);
		}
		return infoList;

	}

	public void updateUserDetail(User user, UserDetail userDetail) {
		userDetail.setEmployeeNumber(user.getEmployeeNumber());
		userDetailRepository.saveAndFlush(userDetail);
	}

	public void updateToppageOrder(User user, List<Toppage> order) {
		List<Toppage> del = toppageRepository.findAllByEmployeeNumberOrderByToppageOrderAsc(user.getEmployeeNumber());
		toppageRepository.delete(del);
		for (int i = 0; i < order.size(); i++) {
			order.get(i).setEmployeeNumber(user.getEmployeeNumber());
			toppageRepository.saveAndFlush(order.get(i));
		}
	}

	public void updateMonitoringTags(User user, List<NotificationConfig> notification) {
		NotificationConfig ref = new NotificationConfig();
		for (int i = 0; i < notification.size(); i++) {
			ref = notificationConfigRepository.findByEmployeeNumberAndTagId(user.getEmployeeNumber(),
					notification.get(i).getTagId());
			notification.get(i).setEmployeeNumber(user.getEmployeeNumber());
			if (ref != null){
				notification.get(i).setMailNotification(ref.isMailNotification());
				notification.get(i).setBrowserNotification(ref.isBrowserNotification());
			}

		}
		List<NotificationConfig> del = notificationConfigRepository.findByEmployeeNumber(user.getEmployeeNumber());
		notificationConfigRepository.delete(del);
		for(int i = 0; i < notification.size(); i++){
			notificationConfigRepository.saveAndFlush(notification.get(i));
		}

	}

	public void updateNotification(User user, List<NotificationConfig> notification) {
		for (int i = 0; i < notification.size(); i++) {
			notification.get(i).setEmployeeNumber(user.getEmployeeNumber());
			notificationConfigRepository.saveAndFlush(notification.get(i));
		}
	}
}
