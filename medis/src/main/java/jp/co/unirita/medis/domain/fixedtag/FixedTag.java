package jp.co.unirita.medis.domain.fixedtag;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "fixed_tag")
public class FixedTag {

	@Id
	@Size(min = 11, max = 11)
	private String fixedTagId;

	@Size(min = 11, max = 11)
	private String templateId;

	@Size(min = 11, max = 11)
	private String tagId;
}
