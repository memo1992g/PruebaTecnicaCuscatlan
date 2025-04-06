package backend.apiscart.component.exception;

import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import backend.apiscart.dto.exception.ErrorDto;
import lombok.Getter;
import lombok.ToString;

@Component
@Getter
@ToString
public class GenericException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	private final ErrorDto baseError;

	public GenericException() {
		super("error.genericexception.nocontrolled");
		this.baseError = ErrorDto.builder().httpHeaders(new HttpHeaders())
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).field("").build();
	}

	public GenericException(String message) {
		super(message);
		this.baseError = ErrorDto.builder().httpHeaders(new HttpHeaders())
				.httpStatus(HttpStatus.INTERNAL_SERVER_ERROR).field("").build();
	}

	public GenericException(HttpStatus httpStatus, HttpHeaders httpHeaders, String field, String message,
			List<String> errors) {
		super(message);
		this.baseError = ErrorDto.builder().httpHeaders(httpHeaders).httpStatus(httpStatus).field(field)
				.errors(errors).build();
	}

	public GenericException(HttpStatus httpStatus, String field, String message, List<String> errors) {
		super(message);
		this.baseError = ErrorDto.builder().httpHeaders(new HttpHeaders()).httpStatus(httpStatus).field(field)
				.errors(errors).build();
	}

}
