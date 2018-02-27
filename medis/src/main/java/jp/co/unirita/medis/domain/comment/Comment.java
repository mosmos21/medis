package jp.co.unirita.medis.domain.comment;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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

	private String value;

	private boolean read;


}
