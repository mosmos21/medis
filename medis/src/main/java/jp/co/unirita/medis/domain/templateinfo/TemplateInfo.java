package jp.co.unirita.medis.domain.templateinfo;

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
@Table(name = "template_info")
@AllArgsConstructor
@NoArgsConstructor
public class TemplateInfo {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

	@Size(max = 64)
	private String employeeNumber;

	private String templateName;

	private Timestamp templateCreateDate;

	private boolean templatePublish;

}
