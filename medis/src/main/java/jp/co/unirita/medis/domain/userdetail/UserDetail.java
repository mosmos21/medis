package jp.co.unirita.medis.domain.userdetail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "user_detail")
@ToString
public class UserDetail {

	@Id
	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String employeeNumber;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String lastName;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String firstName;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String lastNamePhonetic;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String firstNamePhonetic;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	@Email
	private String mailaddress;

	private boolean icon;

}
