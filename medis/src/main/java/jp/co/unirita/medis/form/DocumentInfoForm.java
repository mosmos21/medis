package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.sql.Timestamp;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import lombok.Data;

@Data
public class DocumentInfoForm implements Serializable {

	public String documentId;
	public String documentTitle;
	public String employeeNumber;
	public Timestamp createDate;
	public boolean documentPublish;


	public DocumentInfoForm(String documentId, String documentTitle, String employeeNumber, Timestamp createDate, boolean isDocumentPublish) {
		this.documentId = documentId;
		this.documentTitle = documentTitle;
		this.employeeNumber = employeeNumber;
		this.createDate = createDate;
		this.documentPublish = isDocumentPublish;
	}

	public DocumentInfoForm(DocumentInfo documentInfo, String documentName) {
		this(documentInfo.getDocumentId(), documentName, documentInfo.getEmployeeNumber(), documentInfo.getDocumentCreateDate(), documentInfo.isDocumentPublish());
	}
}