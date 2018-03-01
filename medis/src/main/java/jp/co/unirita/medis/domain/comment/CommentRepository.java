package jp.co.unirita.medis.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, String> {
	List<Comment> findByDocumentIdOrderByCommentDateAsc(String documentId);
	int countByCommentId(String commentId);
}
