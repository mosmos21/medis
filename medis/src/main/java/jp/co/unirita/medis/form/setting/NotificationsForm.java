package jp.co.unirita.medis.form.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NotificationsForm {

	private String tagId;

	private String tagName;

	private boolean mailNotification;

	private boolean BrowserNotification;

}
