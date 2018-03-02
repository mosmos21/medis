package jp.co.unirita.medis.form.setting;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotificationsForm {
	@Size(min = 11, max = 11, message = "タグIDは11文字で入力してください。")
	@NotBlank(message = "タグIDが入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "タグIDに不正な文字が含まれています。")
	private String tagId;

	@Size(max = 256, message = "タグ名は256文字以内で入力してください。")
	@NotBlank(message = "タグ名が入力されていません。")
	@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "タグ名に不正な文字が含まれています。")
	private String tagName;

	private boolean mailNotification;

	private boolean BrowserNotification;
}
