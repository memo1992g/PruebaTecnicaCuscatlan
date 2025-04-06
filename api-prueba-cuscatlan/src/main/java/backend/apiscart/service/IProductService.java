package backend.apiscart.service;

import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.products.ResponseGetKafestoreapiDto;
import backend.apiscart.dto.products.ResponseServiceKafestoreapi;

public interface IProductService {

	public ResponseServiceKafestoreapi getProducts();
	public GenericResponseServiceDto<ResponseGetKafestoreapiDto> getProductOne(Long id);
}
