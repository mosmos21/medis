package jp.co.unirita.medis.domain.templateflame;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

@Data
@Entity
@IdClass(value = TemplateFlame.PK.class)
@Table(name = "template_flame")
public class TemplateFlame {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

	@Id
	private int templateOrder;

	@Size(min = 11, max = 11)
	private String blockId;

	private String templateBaseHtml;

	private int lineLength;

	private boolean isUserVariable;

	@Data
	public class PK implements Serializable{
		private String templateId;
		private int templateOrder;
	}
}
