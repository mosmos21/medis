package jp.co.unirita.medis.domain.notificationconfig;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class NotificationConfig {

    @Id
    private String employeeNumber;
}
