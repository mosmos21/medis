package jp.co.unirita.medis.domain.documenttag;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "document_tag")
public class DocumentTag {

	@Embedded
	@Size(min = 11, max = 11)
	private String documentId;

	@Embedded
	@Size(min = 11, max = 11)
	private String tagId;

	private boolean isFixed;
}
