package jp.co.unirita.medis.domain.templateinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateInfoRepository extends JpaRepository<TemplateInfo, String> {
    List<TemplateInfo> findByEmployeeNumber(String employeeNumber);
    List<TemplateInfo> findByTemplatePublish(boolean templatePublish);
    List<TemplateInfo> findByEmployeeNumberAndTemplatePublish(String employeeNumber, boolean templatePublish);
}
