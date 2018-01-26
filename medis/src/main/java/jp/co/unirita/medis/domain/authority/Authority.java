package jp.co.unirita.medis.domain.authority;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "authority")
public class Authority {

	@Id
	@Size(min = 11, max = 11)
	private String authorityId;

	@Size(max = 64)
	private String authorityType;
}
