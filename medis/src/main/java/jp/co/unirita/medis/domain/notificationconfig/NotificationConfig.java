package jp.co.unirita.medis.domain.notificationconfig;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@IdClass(value = NotificationConfig.PK.class)
@Table(name = "notification_config")
public class NotificationConfig {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 11, max = 11)
	private String tagId;

	private boolean MailNotification = true;

	private boolean BrowserNotification = true;

    @Data
    public static class PK implements Serializable{
        private String employeeNumber;
        private String tagId;
    }

}
