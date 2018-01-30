package jp.co.unirita.medis.domain.comment;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "comment")
public class Comment {

	@Id
	@Size(min = 11, max = 11)
	private String commentId;

	@Size(min = 11, max = 11)
	private String documentId;

	private Timestamp commentDate;

	@Size(max = 64)
	private String employeeNumber;

	private String commentContent;

	private boolean isRead;
}
