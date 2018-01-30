package jp.co.unirita.medis.domain.userdetail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "user_detail")
public class UserDetail {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Size(max = 64)
	private String lastName;

	@Size(max = 64)
	private String firstName;

	@Size(max = 64)
	private String lastNmaePhonetic;

	@Size(max = 64)
	private String firstNamePhonetic;

	@Size(max = 64)
	private String mailaddress;

	private boolean isIcon;

	@Size(max = 256)
	private String password;
}
