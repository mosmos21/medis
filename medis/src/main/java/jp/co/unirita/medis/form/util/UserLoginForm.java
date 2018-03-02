package jp.co.unirita.medis.form.util;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserLoginForm implements Serializable{
    private String employeeNumber;
    private String password;
}
