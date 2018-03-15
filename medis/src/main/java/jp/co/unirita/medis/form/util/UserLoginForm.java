package jp.co.unirita.medis.form.util;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginForm implements Serializable{
    private String employeeNumber;
    private String password;
}
