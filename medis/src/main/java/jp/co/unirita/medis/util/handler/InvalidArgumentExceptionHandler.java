package jp.co.unirita.medis.util.handler;

import jp.co.unirita.medis.util.exception.InvalidArgumentException;
import jp.co.unirita.medis.util.response.ErrorResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class InvalidArgumentExceptionHandler {

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> getException(HttpServletRequest request, InvalidArgumentException e) {
        return ErrorResponse.creaeResponse(e);
    }
}
