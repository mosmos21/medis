package jp.co.unirita.medis.util.handler;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import jp.co.unirita.medis.util.exception.IdIssuanceUpperException;
import jp.co.unirita.medis.util.response.ErrorResponse;

@ControllerAdvice
public class IdIssuanceUpperExceptionHandler {

	@ExceptionHandler({IdIssuanceUpperException.class})
	public ResponseEntity<ErrorResponse> getException(HttpServletRequest request,IdIssuanceUpperException e) {
        return ErrorResponse.createResponse(e);
    }
}
