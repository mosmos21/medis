package jp.co.unirita.medis.util.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import jp.co.unirita.medis.util.exception.ConflictException;
import jp.co.unirita.medis.util.exception.InvalidArgumentException;
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

    public static ResponseEntity<ErrorResponse> creaeResponse(InvalidArgumentException e) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(e.getInvalidArgument(), e.getValue(), e.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

	public static ResponseEntity<ErrorResponse> creaeResponse(ConflictException e) {
        return new ResponseEntity<ErrorResponse>(
                new ErrorResponse(e.getConflict(), e.getValue(), e.getMessage()),
                HttpStatus.CONFLICT
        );
	}
}
