package jp.co.unirita.medis.domain.templatetag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TemplateTagRepository extends JpaRepository<TemplateTag, TemplateTag.PK> {
	List<TemplateTag> findByTagId(String tagId);
	List<TemplateTag> findByTagIdIn(List<String> tagIds);
	List<TemplateTag> findByTemplateId(String templateId);
	void deleteByTemplateId(String templateId);
}
