package jp.co.unirita.medis.form.document;

import java.io.Serializable;
import java.sql.Timestamp;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfoForm implements Serializable {

	private String documentId;
	private String documentName;
	private String employeeNumber;
	private Timestamp documentCreateDate;
	private String updateType;
	private Timestamp updateDate;

	public DocumentInfoForm(DocumentInfo docInfo, UpdateInfo upInfo) {
		this(docInfo.getDocumentId(), docInfo.getDocumentName(), docInfo.getEmployeeNumber(), docInfo.getDocumentCreateDate(), upInfo.getUpdateType(), upInfo.getUpdateDate());
	}
}