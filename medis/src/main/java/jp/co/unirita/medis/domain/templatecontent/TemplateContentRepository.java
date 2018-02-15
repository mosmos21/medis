package jp.co.unirita.medis.domain.templatecontent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemplateContentRepository extends JpaRepository<TemplateContent, String> {
	String findByTemplateIdAndTemplateOrderAndLineNumber(Integer templateId, Integer templateOrder, Integer lineNumber);
}
