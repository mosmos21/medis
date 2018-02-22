package jp.co.unirita.medis.domain.updateinfo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UpdateInfoRepository extends JpaRepository<UpdateInfo, String> {
	List<UpdateInfo> findByDocumentId(String documentId);
	List<UpdateInfo> findByDocumentIdAndUpdateIdGreaterThan(String documentId, String lastUpdateId);
//	List<String> findByUpdateId();
}
