package backend.apiscart.advicer;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import backend.apiscart.component.exception.GenericException;
import backend.apiscart.component.util.log.LogUtil;
import backend.apiscart.component.util.log.LogUtilImpl;
import backend.apiscart.component.util.log.LogUtil.TYPELOG;
import backend.apiscart.dto.exception.ErrorDto;
import backend.apiscart.dto.exception.ResponseErrorDto;

@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    private final MessageSource messageSource;
    private LogUtil log;

    public GenericExceptionHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
        this.log = new LogUtilImpl(GenericExceptionHandler.class);
    }

    @ExceptionHandler(GenericException.class)
    public final ResponseEntity<Object> handleCustomException(GenericException ex, WebRequest request) {
        return handlerGenericException(ex, request, ex.getBaseError());
    }

    private final ResponseEntity<Object> handlerGenericException(GenericException ex, WebRequest request, ErrorDto baseError) {
        return customResponseFromException(ex, request, baseError);
    }

    private ResponseEntity<Object> customResponseFromException(GenericException ex, WebRequest request, ErrorDto baseError) {
        String message = ex.getMessage();
        ResponseErrorDto response = ResponseErrorDto.builder()
                .field(baseError.getField())
                .status(baseError.getHttpStatus())
                .errors(baseError.getErrors())
                .message(getMessage(message, request.getLocale()))
                .object(ex.getClass().getSimpleName())
                .build();
        log.write(TYPELOG.ERROR, "Response Message", response);
        return new ResponseEntity<>(response, baseError.getHttpHeaders(), baseError.getHttpStatus());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exception(Exception ex, WebRequest request) {
        var baseError = ErrorDto.builder()
                .field("Exception")
                .httpHeaders(new HttpHeaders())
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .errors(List.of(ex.getMessage()))
                .build();

        GenericException generic = new GenericException(
                HttpStatus.INTERNAL_SERVER_ERROR,
                new HttpHeaders(),
                ex.getMessage(),
                "error.generic",
                List.of(ex.getMessage())
        );

        return customResponseFromException(generic, request, baseError);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {

        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            String message = getMessage(error.getDefaultMessage(), request.getLocale());
            errors.add(error.getField() + ": " + message);
        }
        for (ObjectError error : ex.getBindingResult().getGlobalErrors()) {
            String message = getMessage(error.getDefaultMessage(), request.getLocale());
            errors.add(error.getObjectName() + ": " + message);
        }

        GenericException apiError = new GenericException(
                HttpStatus.BAD_REQUEST,
                headers,
                getMessage("error.genericexception.inputvalidation", request.getLocale()),
                "error.genericexception.badrequest",
                errors
        );

        return customResponseFromException(apiError, request, apiError.getBaseError());
    }

    private String getMessage(String messageCode, Locale locale) {
        try {
            return messageSource.getMessage(messageCode, null, locale);
        } catch (Exception ex) {
            return messageCode;
        }
    }
}
