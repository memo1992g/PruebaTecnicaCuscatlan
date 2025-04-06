package backend.apiscart.dto.exception;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ErrorDto implements Serializable {
    private static final long serialVersionUID = 1L;
	private HttpHeaders httpHeaders;
    private HttpStatus httpStatus;
    private String field;
    private List<String> errors;
}
