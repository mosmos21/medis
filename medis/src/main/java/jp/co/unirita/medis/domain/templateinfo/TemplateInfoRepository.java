package jp.co.unirita.medis.domain.templateinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thymeleaf.Template;

import java.util.List;

public interface TemplateInfoRepository extends JpaRepository<TemplateInfo, String> {
    List<TemplateInfo> findByEmployeeNumber(String employeeNumber);
    List<TemplateInfo> findByTemplatePublish(boolean templatePublish);
    List<TemplateInfo> findByEmployeeNumberAndTemplatePublish(String employeeNumber, boolean templatePublish);
}
