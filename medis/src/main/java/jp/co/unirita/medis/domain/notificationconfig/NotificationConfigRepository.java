package jp.co.unirita.medis.domain.notificationconfig;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationConfigRepository extends JpaRepository<NotificationConfig, String> {
	List<NotificationConfig> findByEmployeeNumber(String employeeNumber);

}
