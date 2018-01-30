package jp.co.unirita.medis.domain.documenttag;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class DocumentTag {

    @Id
    private String documentId;

}
