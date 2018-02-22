package jp.co.unirita.medis.util.exception;

import lombok.Data;

@Data
public class InvalidUserException extends Exception{

    String invalidUser;
    String value;
    String message;

    public InvalidUserException(String invalidUser, String value, String message){
        this.invalidUser = invalidUser;
        this.value = value;
        this.message = message;
    }
}
