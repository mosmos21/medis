package jp.co.unirita.medis.domain.documentInfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, String>{

    List<DocumentInfo> findByDocumentId(String documentId);
    List<DocumentInfo> findByTemplateId(String templateId);
    List<DocumentInfo> findByEmployeeNumber(String employeeNumber);
    List<DocumentInfo> findByDocumentIdIn(List<String> documentIds);
    List<DocumentInfo> findByTemplateIdIn(List<String> templateIds);
    List<DocumentInfo> findByEmployeeNumberAndDocumentPublish(String employeeNumber, boolean documentPublish);
    int countByDocumentId(String documentId);
}
