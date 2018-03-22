package jp.co.unirita.medis.form.system;

import lombok.Data;

@Data

public class SnackbarNotificationsForm {
	private String documentId;

	private String updateType;

	private String updateId;

	private String documentName;

	public SnackbarNotificationsForm(String documentId, String updateType, String updateId, String documentName) {
		this.documentId = documentId;
		this.updateType = updateType;
		this.updateId = updateId;
		this.documentName = documentName;
	}
}
