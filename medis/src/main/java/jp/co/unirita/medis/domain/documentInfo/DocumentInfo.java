package jp.co.unirita.medis.domain.documentInfo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "document_info")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInfo {

	@Id
	@Size(min = 11, max = 11)
	private String documentId;

	@Size(max = 64)
	private String documentName;

	@Size(max = 64)
	private String employeeNumber;

	@Size(min = 11, max = 11)
	private String templateId;

	private Timestamp documentCreateDate;

	private boolean documentPublish;
}
