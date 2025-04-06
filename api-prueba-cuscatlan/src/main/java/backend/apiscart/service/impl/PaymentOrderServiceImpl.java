package backend.apiscart.service.impl;

import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import backend.apiscart.dao.IOrderRegistrationDao;
import backend.apiscart.dao.IPaymentOrderDao;
import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.payment.PaymentOrderDto;
import backend.apiscart.dto.payment.PaymentOrderUpdateDto;
import backend.apiscart.model.OrderRegistration;
import backend.apiscart.model.PaymentOrder;
import backend.apiscart.service.IPaymentOrderService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentOrderServiceImpl implements IPaymentOrderService {
	private final IOrderRegistrationDao orderDao;
	private final ModelMapper modelMapper;
	private final IPaymentOrderDao paymentDao;
	
	@Override
	public GenericResponseServiceDto<Object> create(PaymentOrderDto request) {
		var response = new GenericResponseServiceDto<Object>();
		try {
			Optional<OrderRegistration> order = orderDao.findById(request.getIdOrder());
					
			if (order.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("Error the order does not exist");
				return response;
			}
			Optional<PaymentOrder> paymentOpt = paymentDao.findByPayment(request.getIdOrder());
			if (!paymentOpt.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("The payment was already made");
				return response;
			}
			
			PaymentOrder payment = mapperPaymentDtoToPayment(request);

			paymentDao.save(payment);
			response.setSuccess(true);
			response.setMessage("payment saved successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("An error occurred while saving");
		}
		
		return response;
	}

	@Override
	public GenericResponseServiceDto<Object> update(PaymentOrderUpdateDto request) {
		var response = new GenericResponseServiceDto<Object>();
		try {
			Optional<OrderRegistration> order = orderDao.findById(request.getIdOrder());
					
			if (order.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("Error the order does not exist");
				return response;
			}
			Optional<PaymentOrder> paymentOpt = paymentDao.findByPayment(request.getId());
			if (paymentOpt.isEmpty()) {
				response.setSuccess(false);
				response.setMessage("Payment does not exist");
				return response;
			}
			
			PaymentOrder payment = mapperPaymentUpdateDtoToPayment(request);

			paymentDao.save(payment);
			response.setSuccess(true);
			response.setMessage("payment saved successfully");
			
		} catch (Exception e) {
			e.printStackTrace();
			response.setSuccess(false);
			response.setMessage("An error occurred while saving");
		}
		
		return response;
	}
	
	private PaymentOrder mapperPaymentDtoToPayment(PaymentOrderDto request) {
		PaymentOrder payment= modelMapper.map(request, PaymentOrder.class);
	    return payment;
	}
	
	private PaymentOrder mapperPaymentUpdateDtoToPayment(PaymentOrderUpdateDto request) {
		PaymentOrder payment= modelMapper.map(request, PaymentOrder.class);
	    return payment;
	}
	
}
