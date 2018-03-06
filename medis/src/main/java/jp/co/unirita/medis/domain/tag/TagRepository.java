package jp.co.unirita.medis.domain.tag;

import java.util.Collection;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, String> {
	Tag findByTagId(String tagId);
	List<Tag> findByTagId(Collection<String> idList);
	List<Tag> findByTagIdNotOrderByTagIdDesc(String tagId);
	List<Tag> findByTagName(String tagName);
}
