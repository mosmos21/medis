package jp.co.unirita.medis.form;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserLoginForm implements Serializable{
    public String employeeNumber;
    public String password;
}
