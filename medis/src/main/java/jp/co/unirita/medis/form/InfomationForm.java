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

	public String updateId;
	public String documentId;
	public String documentName;
	public String updateType;
	public String employeeNumber;
	public Timestamp updateDate;

	public InfomationForm(UpdateInfo info, String documentName) {
		this(info.getUpdateId(), info.getDocumentId(), documentName, info.getUpdateType(), info.getEmployeeNumber(), info.getUpdateDate());
	}
}
