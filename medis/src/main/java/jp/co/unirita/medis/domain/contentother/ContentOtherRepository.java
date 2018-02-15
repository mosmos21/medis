package jp.co.unirita.medis.domain.contentother;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ContentOtherRepository extends JpaRepository<ContentOther, String> {
	List<ContentOther> findByContentId(String contentId);
}
