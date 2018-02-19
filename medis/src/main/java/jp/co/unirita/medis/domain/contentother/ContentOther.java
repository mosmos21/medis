package jp.co.unirita.medis.domain.contentother;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "content_other")
public class ContentOther {

	@Id
	@Size(min = 11, max = 11)
	private String contentId;

	private String contentMain;
}
