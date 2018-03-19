package jp.co.unirita.medis.form.setting;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnackbarNotificationsForm {
	private String documentId;

	private String updateType;

	private String DocumentName;

}
