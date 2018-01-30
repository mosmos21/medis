package jp.co.unirita.medis.domain.tempkeyInfo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TempkeyInfo {

    @Id
    private String tempKeyId;
}