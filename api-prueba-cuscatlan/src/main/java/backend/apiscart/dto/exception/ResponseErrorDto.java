package backend.apiscart.dto.exception;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import backend.apiscart.component.util.DateUtils;

import org.springframework.http.HttpStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseErrorDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String object;
    private HttpStatus status;
    private String field;
    private String errorCode;
    private String message;
    private List<String> errors;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss:SSS", timezone = "America/El_Salvador")
    private String timestamp = DateUtils.formaterDate();
}
