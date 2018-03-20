package jp.co.unirita.medis.domain.updateinfo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "update_info")
public class UpdateInfo {

	@Id
	@Size(min = 11, max = 11)
	private String updateId;

	@Size(min = 11, max = 11)
	private String documentId;

	private String updateType;

	// updateType v0000000002,v0000000003の時のemployeeNumberはコメントした人
	@Size(max = 64)
	private String employeeNumber;

	private Timestamp updateDate;

}
