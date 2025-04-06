package backend.apiscart.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.apiscart.component.util.ServiceFactory;
import backend.apiscart.component.util.constant.ConstantesGeneral;
import backend.apiscart.component.util.log.LogUtil;
import backend.apiscart.component.util.log.LogUtil.TYPELOG;
import backend.apiscart.dto.base.GenericResponseDto;
import backend.apiscart.dto.payment.PaymentOrderDto;
import backend.apiscart.dto.payment.PaymentOrderUpdateDto;
import backend.apiscart.service.IPaymentOrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/paymentOrder")
@RequiredArgsConstructor
public class PaymentOrderController {

	private final LogUtil logs;
	
	private final IPaymentOrderService paymentService;
	
	@PostMapping("")
	@Operation(summary = "Service create a order", description = "Create the order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> create(@RequestBody @Valid PaymentOrderDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / create() backend.shoppingcart.controller, ");

		var respuesta = paymentService.create(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PutMapping("")
	@Operation(summary = "Service create a order", description = "Create the order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> create(@RequestBody @Valid PaymentOrderUpdateDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / create() backend.shoppingcart.controller, ");

		var respuesta = paymentService.update(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
}
