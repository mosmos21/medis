package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.sql.Timestamp;

import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import lombok.Data;

@Data
public class InfomationForm implements Serializable {


	public String updateId;
	public String documentId;
	public String documentName;
	public String updateType;
	public String employeeNumber;
	public Timestamp updateDate;

	public InfomationForm(String updateId, String documentId, String documentName, String updateType,
			String employeeNumber, Timestamp updateDate) {
		this.updateId = updateId;
		this.documentId = documentId;
		this.documentName = documentName;
		this.updateType = updateType;
		this.employeeNumber = employeeNumber;
		this.updateDate = updateDate;
	}


	public InfomationForm(UpdateInfo info, String documentName) {
		this(info.getUpdateId(), info.getDocumentId(), documentName, info.getUpdateType(), info.getEmployeeNumber(), info.getUpdateDate());
	}
}
