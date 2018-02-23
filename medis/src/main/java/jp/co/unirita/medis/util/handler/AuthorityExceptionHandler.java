package jp.co.unirita.medis.util.handler;

import jp.co.unirita.medis.util.exception.AuthorityException;
import jp.co.unirita.medis.util.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class AuthorityExceptionHandler {

    @ExceptionHandler({AuthorityException.class})
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request, AuthorityException e) {
        return ErrorResponse.createResponse(e);
    }
}
