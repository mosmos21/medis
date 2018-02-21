package jp.co.unirita.medis.domain.templatecontent;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Entity
@IdClass(value = TemplateContent.PK.class)
@Table(name = "template_content")
@AllArgsConstructor
@NoArgsConstructor
public class TemplateContent {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

    @Id
    private int contentOrder;

	@Size(min = 11, max = 11)
	private String blockId;

	@Data
    @AllArgsConstructor
    @NoArgsConstructor
	public static class PK implements Serializable{
		private String templateId;
		private int contentOrder;
	}
}
