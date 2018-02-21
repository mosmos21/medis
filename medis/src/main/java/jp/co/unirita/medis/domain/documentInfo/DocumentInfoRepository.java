package jp.co.unirita.medis.domain.documentInfo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, String>{
    List<DocumentInfo> findByEmployeeNumberOrderByDocumentCreateDateAsc(String employeeNumber);
    List<DocumentInfo> findByEmployeeNumberAndDocumentPublishOrderByDocumentCreateDateAsc(String employeeNumber, boolean documentPublish);
    Page<DocumentInfo> findByEmployeeNumber(String EmployeeNumber, Pageable pageble);
    List<DocumentInfo> findByDocumentId(String documentId);
    List<DocumentInfo> findByTemplateId(String templateId);
}
