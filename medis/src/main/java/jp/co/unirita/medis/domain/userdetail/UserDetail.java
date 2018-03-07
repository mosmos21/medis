package jp.co.unirita.medis.domain.userdetail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
@Entity
@Table(name = "user_detail")
public class UserDetail {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Size(max = 64, message = "苗字は64文字以内で入力してください。")
	@NotBlank(message = "苗字が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "苗字に不正な文字が含まれています。")
	private String lastName;

	@Size(max = 64, message = "名前は64文字以内で入力してください。")
	@NotBlank(message = "名前が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "名前に不正な文字が含まれています。")
	private String firstName;

	@Size(max = 64, message = "苗字（ふりがな）は64文字以内で入力してください。")
	@NotBlank(message = "苗字（ふりがな）が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "苗字（ふりがな）に不正な文字が含まれています。")
	private String lastNamePhonetic;

	@Size(max = 64, message = "名前（ふりがな）は64文字以内で入力してください。")
	@NotBlank(message = "名前（ふりがな）が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "名前（ふりがな）に不正な文字が含まれています。")
	private String firstNamePhonetic;

	@Size(max = 64, message = "メールアドレスは64文字以内で入力してください。")
	@NotBlank(message = "メールアドレスが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "メールアドレスに不正な文字が含まれています。")
	@Email(message = "メールアドレスの入力に誤りがあります。")
	private String mailaddress;

	private boolean icon;


}
