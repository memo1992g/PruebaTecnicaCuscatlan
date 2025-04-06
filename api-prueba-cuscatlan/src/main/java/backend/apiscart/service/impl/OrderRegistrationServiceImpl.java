package backend.apiscart.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import backend.apiscart.component.util.DateUtils;
import backend.apiscart.dao.IOrderRegistrationDao;
import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.orders.OrderDetailsCreateDto;
import backend.apiscart.dto.orders.OrderDetailsUpdateDto;
import backend.apiscart.dto.orders.OrderRegistrationCreateDto;
import backend.apiscart.dto.orders.OrderRegistrationDto;
import backend.apiscart.dto.products.RequestGetOneDto;
import backend.apiscart.dto.products.ResponseGetKafestoreapiDto;
import backend.apiscart.model.OrderDetails;
import backend.apiscart.model.OrderRegistration;
import backend.apiscart.service.IOrderRegistrationService;
import backend.apiscart.service.IProductService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderRegistrationServiceImpl implements IOrderRegistrationService {
	private final IOrderRegistrationDao orderDao;
	private final IProductService fakesService;
	private final ModelMapper modelMapper;

	@Override
	public GenericResponseServiceDto<List<OrderRegistrationDto>> getAll() {
		List<OrderRegistrationDto> listDtoOrder = new ArrayList<>();
		var response = new GenericResponseServiceDto<List<OrderRegistrationDto>>();

		List<OrderRegistration> listOrder = orderDao.findAllWithOrderDetails();
		if (listOrder.isEmpty()) {
			response.setMessage("The order list is empty");
			response.setSuccess(false);
			response.setItem(listDtoOrder);
		} else {
			listDtoOrder = mapperListOrderToListOrderDto(listOrder);

			response.setMessage("Successful response");
			response.setSuccess(true);
			response.setItem(listDtoOrder);
		}
		return response;
	}

	@Override
	public GenericResponseServiceDto<OrderRegistrationDto> get(Long id) {
		var response = new GenericResponseServiceDto<OrderRegistrationDto>();

		Optional<OrderRegistration> orderOpt = orderDao.findByIdWithOrderDetails(id);
		if (orderOpt.isEmpty()) {
			response.setMessage("The order was not found");
			response.setSuccess(false);
		} else {
			
			OrderRegistrationDto orderDto = mapperOrderToOrderDto(orderOpt.get());
					
			response.setMessage("Successful response");
			response.setSuccess(true);
			response.setItem(orderDto);
		}
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> create(OrderRegistrationCreateDto request) {
		var response = new GenericResponseServiceDto<Object>();
		try {
			OrderRegistration order = new OrderRegistration();
			int index = 0; 
			for (OrderDetailsCreateDto detailsOrderDto : request.getDetails()) {
				var responseCheckProduct = new GenericResponseServiceDto<ResponseGetKafestoreapiDto>();
				RequestGetOneDto requestCheck = new RequestGetOneDto();
				requestCheck.setIdProducto(detailsOrderDto.getIdProduct());
				
				//Se obtienen los productos de la api por medio del id
				responseCheckProduct = fakesService.getProductOne(detailsOrderDto.getIdProduct());
				
				if (!responseCheckProduct.getSuccess()) {
					response.setSuccess(false);
					response.setMessage("The product does not exist "+requestCheck.getIdProducto());
					return response;
				}
				if (!detailsOrderDto.getPrice().equals(responseCheckProduct.getItem().getPrice())) {
					request.getDetails().get(index).setPrice((responseCheckProduct.getItem().getPrice()));
				}
				index++;
			}
			
			order = mapperOrderCreateDtoToOrder(request);

			orderDao.save(order);
			response.setSuccess(true);
			response.setMessage("Order saved successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("An error occurred while saving");
		}
		
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> update(OrderRegistrationDto request) {
		var response = new GenericResponseServiceDto<Object>();
		try {
			Optional<OrderRegistration> orderOpt = orderDao.findById(request.getId());
			
			if (orderOpt.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("The order does not exist "+request.getId());
				return response;
			}
			
			OrderRegistration order = orderOpt.get();
			int index = 0;
			for (OrderDetailsUpdateDto detailsUpdateDto : request.getDetails()) {
				var responseCheckProduct = new GenericResponseServiceDto<ResponseGetKafestoreapiDto>();
				RequestGetOneDto requestCheck = new RequestGetOneDto();
				
				//Se obtienen los productos de la api por medio del id
				responseCheckProduct = fakesService.getProductOne(detailsUpdateDto.getIdProduct());
				
				if (!responseCheckProduct.getSuccess()) {
					response.setSuccess(false);
					response.setMessage("The product does not exist "+requestCheck.getIdProducto());
					return response;
				}
				if (!detailsUpdateDto.getPrice().equals(responseCheckProduct.getItem().getPrice())) {
					request.getDetails().get(index).setPrice((responseCheckProduct.getItem().getPrice()));
				}
				index++;
			}
			order = mapperOrderUpdateDtoToOrder(request, order);
			orderDao.save(order);
			response.setSuccess(true);
			response.setMessage("Order updated successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("Ocurrio un error al guardar");
		}
		
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> delete(Long id) {
		var response = new GenericResponseServiceDto<Object>();

		Optional<OrderRegistration> orderOpt = orderDao.findById(id);
		if (orderOpt.isEmpty()) {
			response.setMessage("The order does not exist");
			response.setSuccess(false);
		} else {
			
			OrderRegistration order = orderOpt.get();
			orderDao.delete(order);
					
			response.setMessage("Successful response");
			response.setSuccess(true);
		}
		return response;
	}
	
	private OrderRegistrationDto mapperOrderToOrderDto(OrderRegistration request) {
		OrderRegistrationDto order= modelMapper.map(request, OrderRegistrationDto.class);
		order.setDetails(modelMapper.map(request.getOrderDetails(), new TypeToken<List<OrderDetailsUpdateDto>>() {}.getType()));
	    return order;
	}
	
	private List<OrderRegistrationDto> mapperListOrderToListOrderDto(List<OrderRegistration> request) {
		List<OrderRegistrationDto> listOrder = new ArrayList<>();
		for (OrderRegistration order : request) {
			OrderRegistrationDto orderUpd = modelMapper.map(order, OrderRegistrationDto.class);
			orderUpd.setDetails(modelMapper.map(order.getOrderDetails(), new TypeToken<List<OrderDetailsUpdateDto>>() {}.getType()));
			listOrder.add(orderUpd);
		}
	    return listOrder;
	}

	private OrderRegistration mapperOrderCreateDtoToOrder(OrderRegistrationCreateDto request) {
	    OrderRegistration order = modelMapper.map(request, OrderRegistration.class);
	    List<OrderDetails> detailsList = new ArrayList<>();
	    BigDecimal total = BigDecimal.ZERO;

	    for (OrderDetailsCreateDto detailDto : request.getDetails()) {
	        OrderDetails orderDetail = new OrderDetails();
	        orderDetail.setOrder(order); // Asocia el detalle con la orden
	        orderDetail.setQuantity(detailDto.getQuantity());
	        orderDetail.setPrice(detailDto.getPrice());
	        BigDecimal quantityBigDecimal = BigDecimal.valueOf(detailDto.getQuantity());
	        BigDecimal subtotal = quantityBigDecimal.multiply(detailDto.getPrice());
	        orderDetail.setSubtotal(subtotal);
	        total = total.add(subtotal);
	        detailsList.add(orderDetail);
	    }
	    order.setOrderDetails(detailsList);
	    order.setTotal(total);
		order.setCreationDate(DateUtils.getCurrentTimeInElSalvador());
	    return order;
	}
	
	
	private OrderRegistration mapperOrderUpdateDtoToOrder(OrderRegistrationDto request, OrderRegistration orderModel) {
	    OrderRegistration order = modelMapper.map(request, OrderRegistration.class);
	    List<OrderDetails> detailsList = new ArrayList<>();
	    BigDecimal total = BigDecimal.ZERO;

	    for (OrderDetailsUpdateDto detailDto : request.getDetails()) {
	        OrderDetails orderDetail = new OrderDetails();
	        orderDetail.setId(detailDto.getId());
	        orderDetail.setOrder(order); // Asocia el detalle con la orden
	        orderDetail.setQuantity(detailDto.getQuantity());
	        orderDetail.setPrice(detailDto.getPrice());
	        BigDecimal quantityBigDecimal = BigDecimal.valueOf(detailDto.getQuantity());
	        BigDecimal subtotal = quantityBigDecimal.multiply(detailDto.getPrice());
	        orderDetail.setSubtotal(subtotal);
	        total = total.add(subtotal);
	        detailsList.add(orderDetail);
	    }
	    order.setOrderDetails(detailsList);
	    order.setTotal(total);
		order.setCreationDate(orderModel.getCreationDate());
		order.setModificationDate(DateUtils.getCurrentTimeInElSalvador());
	    return order;
	}
}
