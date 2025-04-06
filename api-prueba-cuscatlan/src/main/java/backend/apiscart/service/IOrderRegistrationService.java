package backend.apiscart.service;

import java.util.List;

import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.orders.OrderRegistrationCreateDto;
import backend.apiscart.dto.orders.OrderRegistrationDto;

public interface IOrderRegistrationService {
	public GenericResponseServiceDto<OrderRegistrationDto> get(Long id);
	public GenericResponseServiceDto<List<OrderRegistrationDto>> getAll();
	public GenericResponseServiceDto<Object> create(OrderRegistrationCreateDto rquest);
	public GenericResponseServiceDto<Object> update(OrderRegistrationDto request);
	public GenericResponseServiceDto<Object> delete(Long id);
}
