package jp.co.unirita.medis.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConflictException extends Exception{
    String conflict;
    String value;
    String message;
}
