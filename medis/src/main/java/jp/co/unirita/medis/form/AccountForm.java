package jp.co.unirita.medis.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class AccountForm {
	@Size(max = 64, message = "社員番号は64文字以内で入力してください。")
	@NotBlank(message = "社員番号が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "社員番号に不正な文字が含まれています。")
	private String employeeNumber;

	@Size(max = 64, message = "メールアドレスは64文字以内で入力してください。")
	@NotBlank(message = "メールアドレスが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "メールアドレスに不正な文字が含まれています。")
	@Email(message = "メールアドレスの入力に誤りがあります。")
	private String mailaddress;

	@Size(max = 256, message = "パスワードは256文字以内で入力してください。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "パスワードに不正な文字が含まれています。")
	private String password;

}
