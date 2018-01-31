package jp.co.unirita.medis.domain.templateflame;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@Table(name = "template_flame")
public class TemplateFlame {

	@Embedded
	@Size(min = 11, max = 11)
	private String templateId;

	@Embedded
	private int templateOrder;

	@Size(min = 11, max = 11)
	private String blockId;

	private String templateBaseHtml;

	private int lineLength;

	private boolean isUserVariable;
}
