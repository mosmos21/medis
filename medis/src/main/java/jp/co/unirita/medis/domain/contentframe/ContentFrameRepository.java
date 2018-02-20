package jp.co.unirita.medis.domain.contentframe;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentFrameRepository extends JpaRepository<ContentFrame, String>{
	List<ContentFrame> findByDocumentIdAndContentOrderAndLineNumber(String documentId, Integer contentOrder, Integer lineNumber);
	List<ContentFrame > findByDocumentId(String documentId);
	List<ContentFrame > findByContentOrderAndDocumentId(int contentOrder,String documentId);
}
