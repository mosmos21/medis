package jp.co.unirita.medis.domain.tempkeyInfo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@IdClass(TempkeyInfo.PK.class)
@Table(name = "tempkey_info")
@AllArgsConstructor
@NoArgsConstructor
public class TempkeyInfo {

	@Id
	@Size(max = 64)
	private String employeeNumber;

	@Id
	@Size(min = 32, max = 32)
	private String tempKey;

	private Timestamp changeDate;

	@Data
	@AllArgsConstructor
	@NoArgsConstructor
	public static class PK implements Serializable {
		private String employeeNumber;
		private String tempKey;
	}
}
