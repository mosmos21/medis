package jp.co.unirita.medis.domain.contentflame;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "content_flame")
public class ContentFlame {

	@Id
	@Size(min = 11, max = 11)
	private String contentId;

	@Size(min = 11, max = 11)
	private String documentId;

	private int contentOrder;

	private int lineNumber;

	private boolean isTextarea;
}
