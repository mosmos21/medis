package jp.co.unirita.medis.form.document;

import java.sql.Timestamp;

import jp.co.unirita.medis.domain.documentInfo.DocumentInfo;
import jp.co.unirita.medis.domain.userdetail.UserDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfoForm {
	private String documentId;
	private String documentName;
	private String employeeNumber;
	private String lastName;
	private String firstName;
	private String name;
	private String templateId;
	private Timestamp documentCreateDate;
	private boolean documentPublish;

	public DocumentInfoForm(DocumentInfo doc, UserDetail user) {
		this(doc.getDocumentId(), doc.getDocumentName(), doc.getEmployeeNumber(), user.getLastName(), user.getFirstName()
				, user.getLastName() + " " + user.getFirstName(), doc.getTemplateId(), doc.getDocumentCreateDate(), doc.isDocumentPublish());
	}
}
