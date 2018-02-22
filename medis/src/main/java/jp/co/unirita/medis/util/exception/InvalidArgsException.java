package jp.co.unirita.medis.util.exception;

import lombok.Data;

@Data
public class InvalidArgsException extends Exception{

    String invalidArgument;
    String value;
    String message;

    public InvalidArgsException(String invalidArgument, String value, String message){
        this.invalidArgument = invalidArgument;
        this.value = value;
        this.message = message;
    }
}
