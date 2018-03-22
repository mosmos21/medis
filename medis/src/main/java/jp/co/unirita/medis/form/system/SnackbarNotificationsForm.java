package jp.co.unirita.medis.form.system;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SnackbarNotificationsForm {
	private String documentId;

	private String updateType;

	private String updateId;

	private String documentName;

}
