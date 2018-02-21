package jp.co.unirita.medis.domain.documentitem;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@Table(name = "content_flame")
public class DocumentItem {

	@Size(min = 11, max = 11)
	private String documentId;

	private int contentOrder;

	private int lineNumber;

	private String value;

	@Data
	public static class PK implements Serializable {
		private String documentId;
		private int templateOrder;
		private int lineNumber;
	}
}
