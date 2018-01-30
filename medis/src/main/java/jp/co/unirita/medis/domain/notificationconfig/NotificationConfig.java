package jp.co.unirita.medis.domain.notificationconfig;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "notification_config")
public class NotificationConfig {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 11, max = 11)
	private String tagId;

	private boolean isMailNotification;

	private boolean isBrowserNotification;
}
