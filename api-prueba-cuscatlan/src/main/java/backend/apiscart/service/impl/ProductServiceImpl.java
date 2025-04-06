package backend.apiscart.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import backend.apiscart.component.util.constant.ConstantesGeneral;
import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.products.ResponseGetKafestoreapiDto;
import backend.apiscart.dto.products.ResponseServiceKafestoreapi;
import backend.apiscart.service.IProductService;
import backend.apiscart.service.consumer.Consumer;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements IProductService {
	
	private final Consumer consumer;
	private final List<ResponseGetKafestoreapiDto> servicioGetKafestoreapi() {

		Map<String, String> headers = new HashMap<>();
		headers.put(ConstantesGeneral.NCONTENTTYPE, ConstantesGeneral.APPLICATIONJSON);
		headers.put(ConstantesGeneral.ACCEPTLANGUAGE, ConstantesGeneral.LANGUAGE);

		consumer.setDefaultHeaders(headers);

		return consumer.getOneByQueryStringList(ConstantesGeneral.URL_BASE_FAKESTOREAPI, "/products")
				.block();
	}
	
	private ResponseGetKafestoreapiDto servicioGetKafestoreapiOne(Long id) {

		Map<String, String> headers = new HashMap<>();
		headers.put(ConstantesGeneral.NCONTENTTYPE, ConstantesGeneral.APPLICATIONJSON);
		headers.put(ConstantesGeneral.ACCEPTLANGUAGE, ConstantesGeneral.LANGUAGE);

		consumer.setDefaultHeaders(headers);

		return consumer.getOneByQueryStringOne(ConstantesGeneral.URL_BASE_FAKESTOREAPI, "/products/"+id, ResponseGetKafestoreapiDto.class)
				.block();
	}

	@Override
	public ResponseServiceKafestoreapi getProducts() {
		ResponseServiceKafestoreapi respuesta = new ResponseServiceKafestoreapi();
		List<ResponseGetKafestoreapiDto> responseGetKafes = servicioGetKafestoreapi();
		if (responseGetKafes == null) {
			respuesta.setSuccess(false);
			respuesta.setMessage("No products found");
		}else {
			respuesta.setItem(responseGetKafes);
			respuesta.setMessage("Successful process");
			respuesta.setSuccess(true);
		}
		return respuesta;
	}

	@Override
	public GenericResponseServiceDto<ResponseGetKafestoreapiDto> getProductOne(Long id) {
		var response = new GenericResponseServiceDto<ResponseGetKafestoreapiDto>();
		ResponseGetKafestoreapiDto responseGetKafes = servicioGetKafestoreapiOne(id);
		if (responseGetKafes == null) {
			response.setSuccess(false);
			response.setMessage("No products found");
		}else {
			response.setItem(responseGetKafes);
			response.setMessage("Successful process");
			response.setSuccess(true);
		}
		return response;
	}
}
