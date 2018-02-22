package jp.co.unirita.medis.domain.documentitem;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentItemRepository extends JpaRepository<DocumentItem, DocumentItem.PK>{
	List<DocumentItem> findByDocumentIdOrderByContentOrderAscLineNumberAsc(String documentId);
}
