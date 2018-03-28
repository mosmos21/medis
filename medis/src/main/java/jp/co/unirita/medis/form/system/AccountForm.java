package jp.co.unirita.medis.form.system;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AccountForm {

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String employeeNumber;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	@Email
	private String mailaddress;

	@Size(max = 256)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	private String password;

}

