package jp.co.unirita.medis.form;

import java.sql.Timestamp;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import lombok.Data;

@Data
public class MonitoringForm {

	public String documentId;
	public String documentTitle;
	public String employeeNumber;
	public Timestamp createDate;
	public boolean documentPublish;


	public MonitoringForm(String documentId, String documentTitle, String employeeNumber, Timestamp createDate, boolean documentPublish) {
		this.documentId = documentId;
		this.documentTitle = documentTitle;
		this.employeeNumber = employeeNumber;
		this.createDate = createDate;
		this.documentPublish = documentPublish;
	}

	public MonitoringForm(DocumentInfo documentInfo, String documentName) {
		this(documentInfo.getDocumentId(), documentName, documentInfo.getEmployeeNumber(), documentInfo.getDocumentCreateDate(), documentInfo.isDocumentPublish());
	}
}
