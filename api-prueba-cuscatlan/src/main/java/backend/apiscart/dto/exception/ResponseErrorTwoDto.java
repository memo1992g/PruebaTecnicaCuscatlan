package backend.apiscart.dto.exception;

import java.io.Serializable;
import java.util.List;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import backend.apiscart.component.util.DateUtils;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ResponseErrorTwoDto implements Serializable {
	private static final long serialVersionUID = 1L;
	private String object;
    private HttpStatus status;
    private String field;
    private String message;
    private String errorCode;
    private List<String> errors;
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss:SSS", timezone = "America/El_Salvador")
    private String timestamp = DateUtils.formaterDate();
}
