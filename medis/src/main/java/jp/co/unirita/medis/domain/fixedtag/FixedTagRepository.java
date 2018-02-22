package jp.co.unirita.medis.domain.fixedtag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FixedTagRepository extends JpaRepository<TemplateTag, String> {
	List<TemplateTag> findByTagId(String tagId);
}
