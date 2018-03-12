package jp.co.unirita.medis.domain.documenttag;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentTagRepository extends JpaRepository<DocumentTag, DocumentTag.PK> {
	List<DocumentTag> findByTagId(String tagId);
	List<DocumentTag> findByTagIdIn(List<String> tagIds);
	List<DocumentTag> findByDocumentId(String documentId);
}
