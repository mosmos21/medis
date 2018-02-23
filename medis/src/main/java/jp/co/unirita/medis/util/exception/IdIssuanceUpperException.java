package jp.co.unirita.medis.util.exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class IdIssuanceUpperException extends Exception {
    String message;
}
