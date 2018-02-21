package jp.co.unirita.medis.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class UserManagementForm {
	@Size(max = 64, message = "社員番号は64文字以内で入力してください。")
	@NotBlank(message = "社員番号が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "社員番号に不正な文字が含まれています。")
	public String employeeNumber;

	@Size(max = 64, message = "名前は64文字以内で入力してください。")
	@NotBlank(message = "名前が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "名前に不正な文字が含まれています。")
	public String lastName;

	@Size(max = 64, message = "苗字は64文字以内で入力してください。")
	@NotBlank(message = "苗字が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "苗字に不正な文字が含まれています。")
	public String firstName;

	@Size(max = 64, message = "名前（ふりがな）は64文字以内で入力してください。")
	@NotBlank(message = "名前（ふりがな）が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "名前（ふりがな）に不正な文字が含まれています。")
	public String lastNamePhonetic;

	@Size(max = 64, message = "苗字（ふりがな）は64文字以内で入力してください。")
	@NotBlank(message = "苗字（ふりがな）が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "苗字（ふりがな）に不正な文字が含まれています。")
	public String firstNmaePhonetic;

	@Size(max = 64, message = "メールアドレスは64文字以内で入力してください。")
	@NotBlank(message = "メールアドレスが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "メールアドレスに不正な文字が含まれています。")
	@Email(message = "メールアドレスの入力に誤りがあります。")
	public String mailaddress;

	public boolean Icon;

	//public String authorityType;

	public boolean Enabled;

	@Size(min = 11, max = 11, message = "権限IDは11文字で入力してください。")
	@NotBlank(message = "権限IDが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "権限IDに不正な文字が含まれています。")
	public String authorityId;

	@Size(max = 256, message = "パスワードは256文字以内で入力してください。")
	@NotBlank(message = "パスワードが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "パスワードに不正な文字が含まれています。")
	public String password;

}
