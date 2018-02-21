package jp.co.unirita.medis.domain.templateitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface TemplateItemRepository extends JpaRepository<TemplateItem, TemplateItem.PK> {
	List<TemplateItem> findByTemplateIdAndContentOrderOrderByLineNumberAsc(String templateId, int contentOrder);
}
