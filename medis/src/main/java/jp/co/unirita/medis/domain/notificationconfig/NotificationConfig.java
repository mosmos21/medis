package jp.co.unirita.medis.domain.notificationconfig;

import javax.persistence.*;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

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

	private boolean isMailNotification;

	private boolean isBrowserNotification;

    @Data
    public static class PK implements Serializable{
        private String employeeNumber;
        private String tagId;
    }

}
