package jp.co.unirita.medis.domain.templatecontent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TemplateContentRepository extends JpaRepository<TemplateContent, String>{
    List<TemplateContent> findByTemplateIdOrderByContentOrder(String id);
}
