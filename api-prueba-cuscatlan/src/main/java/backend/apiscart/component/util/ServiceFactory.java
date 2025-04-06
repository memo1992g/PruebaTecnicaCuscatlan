package backend.apiscart.component.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import backend.apiscart.component.util.constant.ConstantesGeneral;
import backend.apiscart.component.util.log.LogUtil;
import backend.apiscart.component.util.log.LogUtilImpl;
import backend.apiscart.component.util.log.LogUtil.TYPELOG;
import backend.apiscart.dto.base.GenericResponseDto;
import backend.apiscart.dto.exception.ResponseErrorDto;

public class ServiceFactory {

	private static LogUtil logs= new LogUtilImpl(ServiceFactory.class);
	private ServiceFactory() {
		throw new IllegalStateException("ServiceFactory class component util");
	}

	public static ResponseEntity<GenericResponseDto<Object>> createResponse(Object result) {
		try {
			var genericResponse = new GenericResponseDto<Object>();
			genericResponse.setMessage(ConstantesGeneral.STATUS_OK);
			genericResponse.setStatus(HttpStatus.OK.toString());
			genericResponse.setItem(result);
			return new ResponseEntity<>(genericResponse, HttpStatus.OK);
		} catch (Exception e) {
			logs.write(TYPELOG.ERROR, ConstantesGeneral.LOG_ERROR,"ServiceFactory / createResponse(), "+ e);
			var genericResponse = new GenericResponseDto<Object>();
			genericResponse.setMessage(e.getLocalizedMessage());
			genericResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			genericResponse.setItem(result);
			return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public static ResponseEntity<GenericResponseDto<Object>> notFoundResponse(Object result, Object message) {
		ResponseErrorDto baseError;
		try {
			var genericResponse = new GenericResponseDto<Object>();

			List<String> errors = new ArrayList<>();
			errors.add("Not found");
			baseError = ResponseErrorDto.builder().status(HttpStatus.NOT_FOUND).field("404 NOT_FOUND")
					.message(message.toString()).errorCode("ERROR_NO_DATA_FOUND").errors(errors).build();

			genericResponse.setMessage(message.toString());
			genericResponse.setStatus(HttpStatus.NOT_FOUND.toString());
			genericResponse.setItem(baseError);
			return new ResponseEntity<>(genericResponse, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			logs.write(TYPELOG.ERROR, ConstantesGeneral.LOG_ERROR,"ServiceFactory / notFoundResponse(), "+ e);
			var genericResponse = new GenericResponseDto<Object>();
			genericResponse.setMessage(e.getLocalizedMessage());
			genericResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.toString());
			genericResponse.setItem(result);
			return new ResponseEntity<>(genericResponse, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
