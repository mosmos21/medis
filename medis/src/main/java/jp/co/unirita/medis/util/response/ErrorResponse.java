package jp.co.unirita.medis.util.response;

import jp.co.unirita.medis.util.exception.AuthorityException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.NotExistException;
import lombok.Data;

@Data
public class ErrorResponse {

    String invalidArgument;
    String value;
    String message;

    public ErrorResponse(String invalidArgument, String value, String message){
        this.invalidArgument = invalidArgument;
        this.value = value;
        this.message = message;
    }

    public ResponseEntity<ErrorResponse> createResponse(HttpStatus status) {
        return new ResponseEntity<ErrorResponse>(this, status);
    }

    public static ResponseEntity<ErrorResponse> createResponse(AuthorityException e) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(e.getArgument(), e.getValue(), e.getMessage()),
                HttpStatus.FORBIDDEN
        );
    }

    public static ResponseEntity<ErrorResponse> createResponse(NotExistException e) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(e.getArgument(), e.getValue(), e.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

	public static ResponseEntity<ErrorResponse> createResponse(ConflictException e) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(e.getConflict(), e.getValue(), e.getMessage()),
                HttpStatus.CONFLICT
        );
	}
}
