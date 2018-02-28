package jp.co.unirita.medis.domain.updateinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateInfoRepository extends JpaRepository<UpdateInfo, String> {
	List<UpdateInfo> findByDocumentIdAndUpdateTypeBetween(String documentId, String type1, String type2);
	List<UpdateInfo> findByDocumentIdAndUpdateTypeBetweenAndUpdateIdGreaterThan(String documentId, String start, String end , String lastUpdateId);
	int countByUpdateId(String updateId);
	List<UpdateInfo> findFirst1ByDocumentIdAndUpdateTypeBetweenOrderByUpdateIdDesc(String documentId, String start, String end);

	List<UpdateInfo> findByDocumentIdIn(List<String> doucmentIds);
}
