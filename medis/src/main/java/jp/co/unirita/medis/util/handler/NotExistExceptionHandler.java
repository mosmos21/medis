package jp.co.unirita.medis.util.handler;

import javax.servlet.http.HttpServletRequest;

import jp.co.unirita.medis.util.exception.NotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;

import jp.co.unirita.medis.util.response.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@ControllerAdvice
public class NotExistExceptionHandler {

    @ExceptionHandler({NotExistException.class})
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request, NotExistException e) {
        return ErrorResponse.createResponse(e);
    }
}
