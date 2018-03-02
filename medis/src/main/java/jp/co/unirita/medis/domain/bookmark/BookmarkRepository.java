package jp.co.unirita.medis.domain.bookmark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, String> {
	Bookmark findByEmployeeNumberAndDocumentId(String employeeNumber, String documentId);
	List<Bookmark> findByEmployeeNumberAndSelected(String employeeNumber, boolean Selected);
}
