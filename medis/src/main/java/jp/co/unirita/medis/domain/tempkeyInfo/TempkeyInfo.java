package jp.co.unirita.medis.domain.tempkeyInfo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "tempkey_info")
public class TempkeyInfo {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 32, max = 32)
	private String tempKey;

	private Timestamp changeDate;
}
