package jp.co.unirita.medis.domain.contentflame;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentFlameRepository extends JpaRepository<ContentFlame, String>{
	List<ContentFlame> findByDocumentIdAndContentOrderAndLineNumber(String documentId, Integer contentOrder, Integer lineNumber);
}
