package jp.co.unirita.medis.util.exception;

import lombok.Data;

@Data
public class ConflictException extends Exception{

    String conflict;
    String value;
    String message;

    public ConflictException(String conflict, String value, String message){
        this.conflict = conflict;
        this.value = value;
        this.message = message;
    }
}
