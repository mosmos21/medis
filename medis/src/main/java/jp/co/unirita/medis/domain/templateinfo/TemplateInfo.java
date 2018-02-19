package jp.co.unirita.medis.domain.templateinfo;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "template_info")
public class TemplateInfo {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

	@Size(max = 64)
	private String employeeNumber;

	private String templateName;

	private Timestamp templateCreateDate;

	private boolean isTemplatePublish;

}
