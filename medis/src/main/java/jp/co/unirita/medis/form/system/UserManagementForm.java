package jp.co.unirita.medis.form.system;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import jp.co.unirita.medis.domain.user.User;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class UserManagementForm {

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

	private String name;

	@Size(max = 64)
	@NotBlank
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*")
	@Email
	private String mailaddress;

	private boolean icon;

	private boolean enabled;

	private String authorityId;


	public void applyUserDetail(UserDetail detail) {
		this.employeeNumber = detail.getEmployeeNumber();
		this.lastName = detail.getLastName();
		this.firstName = detail.getFirstName();
		this.lastNamePhonetic = detail.getLastNamePhonetic();
		this.firstNamePhonetic = detail.getFirstNamePhonetic();
		this.name = detail.getLastName() + " " + detail.getFirstName();
		this.mailaddress = detail.getMailaddress();
		this.icon = detail.isIcon();
	}

	public void applyUser(User user) {
        this.authorityId = user.getAuthorityId();
        this.enabled = user.isEnabled();
	}
}
