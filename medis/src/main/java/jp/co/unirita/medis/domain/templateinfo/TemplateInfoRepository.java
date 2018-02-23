package jp.co.unirita.medis.domain.templateinfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.thymeleaf.Template;

import java.util.List;

public interface TemplateInfoRepository extends JpaRepository<TemplateInfo, String> {
    List<TemplateInfo> findByTemplatePublishOrderByTemplateIdAsc(boolean templatePublish);
}
