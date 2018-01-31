package jp.co.unirita.medis.domain.documenttag;

import javax.persistence.*;
import javax.swing.text.Document;
import javax.validation.constraints.Size;

import lombok.Data;

import java.io.Serializable;

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

	private boolean isFixed;

    @Data
    public static class PK implements Serializable{
        private String documentId;
        private String tagId;
    }
}
