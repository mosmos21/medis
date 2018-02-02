package jp.co.unirita.medis.domain.documentInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocumentInfoRepository extends JpaRepository<DocumentInfo, String>{
    List<DocumentInfo> findByEmployeeNumberOrderByDocumentCreateDateAsc(String employeeNumber);
    List<DocumentInfo> findByEmployeeNumberAndIsDocumentPublishOrderByDocumentCreateDateAsc(String employeeNumber, boolean isDocumentPublish);
    Page<DocumentInfo> findByEmployeeNumber(String EmployeeNumber, Pageable pageble);
}
