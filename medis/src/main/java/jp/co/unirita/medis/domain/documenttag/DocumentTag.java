package jp.co.unirita.medis.domain.documenttag;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
@Entity
@IdClass(value = DocumentTag.PK.class)
@Table(name = "document_tag")
public class DocumentTag {

    @Id
    @Size(min = 11, max = 11)
    private String documentId;

    @Id
    @Size(min = 11, max = 11)
    private String tagId;

	private boolean fixed;

    @Data
    public static class PK implements Serializable{
        private String documentId;
        private String tagId;
    }
}
