package jp.co.unirita.medis.domain.bookmark;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, String> {
	List<Bookmark> findByEmployeeNumberAndIsChoiced(String enployeeNumber, boolean isChoiced);
	int countByEmployeeNumberAndDocumentId(String employeeNumber, String documentId);
	List<Bookmark> findByOrderByBookmarkIdDesc();
	List<Bookmark> findByEmployeeNumberAndDocumentId(String employeeNumber, String documentId);
}
