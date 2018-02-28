package jp.co.unirita.medis.domain.tempkeyInfo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TempkeyInfoRepository extends JpaRepository<TempkeyInfo, String> {
	TempkeyInfo findByTempKey(String key);
}
