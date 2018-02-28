package jp.co.unirita.medis.domain.templatetag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateTagRepository extends JpaRepository<TemplateTag, TemplateTag.PK> {
	List<TemplateTag> findByTemplateIdOrderByTagOrderAsc(String templateId);
	List<TemplateTag> findByTagId(String tagId);
	List<TemplateTag> findByTagIdIn(List<String> tagIds);
}
