package jp.co.unirita.medis.util.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorityException extends Exception {
    String argument;
    String value;
    String message;
}
