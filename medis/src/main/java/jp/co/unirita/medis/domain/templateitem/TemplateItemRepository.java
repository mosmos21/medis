package jp.co.unirita.medis.domain.templateitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface TemplateItemRepository extends JpaRepository<TemplateItem, String> {
	String findByTemplateIdAndContentOrderAndLineNumber(int templateId, int contentOrder, int lineNumber);
}
