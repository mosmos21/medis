package jp.co.unirita.medis.domain.tag;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Tag {

    @Id
    private String tagId;
}
