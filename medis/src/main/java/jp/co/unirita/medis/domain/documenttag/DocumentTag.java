package jp.co.unirita.medis.domain.documenttag;

import java.io.Serializable;

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
@IdClass(value = DocumentTag.PK.class)
@Table(name = "document_tag")
@AllArgsConstructor
@NoArgsConstructor
public class DocumentTag {

    @Id
    @Size(min = 11, max = 11)
    private String documentId;

    @Id
    private int tagOrder;

    @Size(min = 11, max = 11)
    private String tagId;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PK {
        private String documentId;
        private int tagOrder;
    }
}
