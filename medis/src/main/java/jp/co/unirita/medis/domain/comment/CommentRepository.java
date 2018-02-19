package jp.co.unirita.medis.domain.comment;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, String> {
	List<Comment> findByDocumentIdOrderByCommentDateAsc(String documentId);
	List<Comment> findByCommentId(String commentId);

}
