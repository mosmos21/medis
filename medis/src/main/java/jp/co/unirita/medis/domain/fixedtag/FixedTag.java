package jp.co.unirita.medis.domain.fixedtag;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class FixedTag {

    @Id
    private String tagId;
}
