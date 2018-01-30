package jp.co.unirita.medis.domain.userdetail;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserDetail {

    @Id
    private String employeeNumber;
}
