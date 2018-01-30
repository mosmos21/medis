package jp.co.unirita.medis.domain.tag;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "tag")
public class Tag {

	@Id
	@Size(min = 11, max = 11)
	private String tagId;

	@Size(max = 256)
	private String tagName;
}
