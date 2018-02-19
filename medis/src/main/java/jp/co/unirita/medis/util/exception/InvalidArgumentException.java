package jp.co.unirita.medis.util.exception;

import lombok.Data;

@Data
public class InvalidArgumentException extends Exception{

    String invalidArgument;
    String value;
    String message;

    public InvalidArgumentException(String invalidArgument, String value, String message){
        this.invalidArgument = invalidArgument;
        this.value = value;
        this.message = message;
    }
}
