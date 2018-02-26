package jp.co.unirita.medis.form;

import lombok.Data;

@Data
public class NotificationCommentForm {
	//@Size(min = 11, max = 11, message = "タグIDは11文字で入力してください。")
	//@NotBlank(message = "タグIDが入力されていません。")
	//@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "タグIDに不正な文字が含まれています。")
	public String employeeNumber;

	//@Size(max = 256, message = "タグ名は256文字以内で入力してください。")
	//@NotBlank(message = "タグ名が入力されていません。")
	//@Pattern(regexp = "(?!.*(http://|https://|\r\n|[\n\r\u2028\u2029\u0085]|<.*>.*</.*>|<.*/>)).*", message = "タグ名に不正な文字が含まれています。")
	public String tagId;

	public boolean mailNotification;

	public boolean BrowserNotification;
}
