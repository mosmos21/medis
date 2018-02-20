package jp.co.unirita.medis.domain.contenttextarea;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContentTextareaRepository extends JpaRepository<ContentTextarea, String> {
	List <ContentTextarea> findByContentId(String cotentId);

}