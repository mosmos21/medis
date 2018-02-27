package jp.co.unirita.medis.domain.templatecontent;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateContentRepository extends JpaRepository<TemplateContent, TemplateContent.PK>{
    List<TemplateContent> findByTemplateIdOrderByContentOrderAsc(String templateId);
}
