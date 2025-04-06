package backend.apiscart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.apiscart.component.util.ServiceFactory;
import backend.apiscart.component.util.constant.ConstantesGeneral;
import backend.apiscart.component.util.log.LogUtil;
import backend.apiscart.component.util.log.LogUtil.TYPELOG;
import backend.apiscart.dto.base.GenericResponseDto;
import backend.apiscart.service.IProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductsController {

	private final LogUtil logs;
	
	private final IProductService fakesService;

	@GetMapping("/getAllProducts")
	@Operation(summary = "Service get products", description = "Return information products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	//@PreAuthorize("hasAuthority('ADMIN')") 
	public ResponseEntity<GenericResponseDto<Object>> getProducts() {
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller KafesstoreapiController  / backend.shoppingcart.controller, ");

		var respuesta = fakesService.getProducts();

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}
	
	@GetMapping("/getAProduct/{idProduct}")
	@Operation(summary = "Servicio get a product", description = "Return information get a product")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request has succeeded"),
			@ApiResponse(responseCode = "404", description = "Not Found") })
	public ResponseEntity<GenericResponseDto<Object>> getProductsOne(@PathVariable Long idProduct) {
	
		logs.write(TYPELOG.INFO, ConstantesGeneral.LOG_INFO,
				"Inicio controller KafesstoreapiController / getProductsOne() backend.shoppingcart.controller, ");

		var respuesta = fakesService.getProductOne(idProduct);

		if (Boolean.FALSE.equals(respuesta.getSuccess())) {
			logs.write(TYPELOG.INFO, respuesta.getMessage(), "No se encontraron productos");

			return ServiceFactory.notFoundResponse(respuesta.getItem(), respuesta.getMessage());
		}

		return ServiceFactory.createResponse(respuesta.getItem());
	}

}
