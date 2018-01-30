package jp.co.unirita.medis.domain.templatecontent;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "template_content")
public class TemplateContent {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

	@Id
	private int templateOrder;

	@Id
	private int lineNumber;

	private String contentHtml;
}
