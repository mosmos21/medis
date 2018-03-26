package jp.co.unirita.medis.domain.templateitem;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemplateItemRepository extends JpaRepository<TemplateItem, TemplateItem.PK> {
	List<TemplateItem> findByTemplateIdAndContentOrderOrderByLineNumberAsc(String templateId, int contentOrder);
	void deleteByTemplateId(String templateId);
}
