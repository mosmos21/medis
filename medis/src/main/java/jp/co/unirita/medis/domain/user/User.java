package jp.co.unirita.medis.domain.user;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "user")
public class User {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Size(min = 11, max = 11)
	private String authorityId;

	private boolean isEnabled;
}
