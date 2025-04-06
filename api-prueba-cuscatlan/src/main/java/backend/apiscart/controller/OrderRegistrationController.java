package backend.apiscart.controller;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import backend.apiscart.dto.orders.OrderRegistrationCreateDto;
import backend.apiscart.dto.orders.OrderRegistrationDto;
import backend.apiscart.service.IOrderRegistrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orderRegistration")
@RequiredArgsConstructor
public class OrderRegistrationController {

	private final LogUtil logs;
	
	private final IOrderRegistrationService orderService;
	
	@GetMapping("/getAllOrders")
	@Operation(summary = "Service get all orders", description = "Return information orders")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> getOrders() {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / getOrders() backend.shoppingcart.controller, ");

		var respuesta = orderService.getAll();

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@GetMapping("/getAnOrder/{idOrder}")
	@Operation(summary = "Service a order", description = "Return information a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> get(@PathVariable Long idOrder) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / get() backend.shoppingcart.controller, ");

		var respuesta = orderService.get(idOrder);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PostMapping("")
	@Operation(summary = "Service create a order", description = "Create the order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> create(@RequestBody @Valid OrderRegistrationCreateDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / create() backend.shoppingcart.controller, ");

		var respuesta = orderService.create(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@PutMapping("")
	@Operation(summary = "Service update a order", description = "Update a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> update(@RequestBody @Valid OrderRegistrationDto request) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / update() backend.shoppingcart.controller, ");

		var respuesta = orderService.update(request);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@DeleteMapping("/deleteOrder/{idOrden}")
	@Operation(summary = "Servicio delete a order", description = "Delete a order")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> delete(@PathVariable Long idOrden) {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller OrderController / delete() backend.shoppingcart.controller, ");

		var respuesta = orderService.delete(idOrden);
		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
}
