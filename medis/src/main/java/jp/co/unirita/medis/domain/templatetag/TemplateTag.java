package jp.co.unirita.medis.domain.templatetag;

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
@IdClass(value = TemplateTag.PK.class)
@Table(name = "template_tag")
@AllArgsConstructor
@NoArgsConstructor
public class TemplateTag {

	@Id
	@Size(min = 11, max = 11)
	private String templateId;

	@Id
    private int tagOrder;

	@Size(min = 11, max = 11)
	private String tagId;

	@Data
    @AllArgsConstructor
    @NoArgsConstructor
	public static class PK implements Serializable {
	    private String templateId;
	    private int tagOrder;
    }
}
