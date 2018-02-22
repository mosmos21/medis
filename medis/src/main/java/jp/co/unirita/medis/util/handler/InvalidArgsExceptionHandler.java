package jp.co.unirita.medis.util.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.co.unirita.medis.util.exception.InvalidArgsException;
import jp.co.unirita.medis.util.response.ErrorResponse;

@ControllerAdvice
public class InvalidArgsExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request, InvalidArgsException e) {
        return ErrorResponse.createResponse(e);
    }
}
