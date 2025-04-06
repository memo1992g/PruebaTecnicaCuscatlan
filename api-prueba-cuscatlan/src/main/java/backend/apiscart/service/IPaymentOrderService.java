package backend.apiscart.service;

import backend.apiscart.dto.base.GenericResponseServiceDto;
import backend.apiscart.dto.payment.PaymentOrderDto;
import backend.apiscart.dto.payment.PaymentOrderUpdateDto;

public interface IPaymentOrderService {
	public GenericResponseServiceDto<Object> create(PaymentOrderDto rquest);
	public GenericResponseServiceDto<Object> update(PaymentOrderUpdateDto rquest);
}
