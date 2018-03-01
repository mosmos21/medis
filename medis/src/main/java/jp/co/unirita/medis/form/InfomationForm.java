package jp.co.unirita.medis.form;

import java.io.Serializable;
import java.sql.Timestamp;

import jp.co.unirita.medis.domain.updateinfo.UpdateInfo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
public class InfomationForm implements Serializable {

	private String updateId;
	private String documentId;
	private String documentName;
	private String updateType;
	private String employeeNumber;
	private Timestamp updateDate;

	public InfomationForm(UpdateInfo info, String documentName) {
		this(info.getUpdateId(), info.getDocumentId(), documentName, info.getUpdateType(), info.getEmployeeNumber(), info.getUpdateDate());
	}
}
