package jp.co.unirita.medis.domain.documentitem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@IdClass(value = DocumentItem.PK.class)
@Table(name = "document_item")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentItem {

	@Id
	@Size(min = 11, max = 11)
	private String documentId;

	@Id
	private int contentOrder;

	@Id
	private int lineNumber;

	@Column(columnDefinition = "TEXT")
	private String value;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PK implements Serializable {
		private String documentId;
		private int contentOrder;
		private int lineNumber;
	}
}
