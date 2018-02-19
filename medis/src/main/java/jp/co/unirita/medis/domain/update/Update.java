package jp.co.unirita.medis.domain.update;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "update")
public class Update {

	@Id
	@Size(min = 11, max = 11)
	private String updateType;

	@Size(max = 64)
	private String updateName;
}
