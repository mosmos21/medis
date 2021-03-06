package jp.co.unirita.medis.domain.updateinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateInfoRepository extends JpaRepository<UpdateInfo, String> {
	UpdateInfo findFirstByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(String documentId, String start, String end);
	List<UpdateInfo> findByDocumentId(String documentId);
	List<UpdateInfo> findByDocumentIdIn(List<String> doucmentIds);
	List<UpdateInfo> findByDocumentIdAndUpdateTypeBetween(String documentId, String type1, String type2);
	List<UpdateInfo> findByDocumentIdAndUpdateTypeBetweenAndUpdateIdGreaterThan(String documentId, String start, String end , String lastUpdateId);
	List<UpdateInfo> findByEmployeeNumber(String employeeNumber);
	List<UpdateInfo> findByUpdateIdAfter(String updateId);

}
