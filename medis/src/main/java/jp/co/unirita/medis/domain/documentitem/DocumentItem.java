package jp.co.unirita.medis.domain.documentitem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@IdClass(value = DocumentItem.PK.class)
@Table(name = "document_item")
public class DocumentItem {

	@Id
	@Size(min = 11, max = 11)
	private String documentId;

	@Id
	private int contentOrder;

	@Id
	private int lineNumber;

	private String value;

	@Data
	public static class PK implements Serializable {
		private String documentId;
		private int contentOrder;
		private int lineNumber;
	}
}
