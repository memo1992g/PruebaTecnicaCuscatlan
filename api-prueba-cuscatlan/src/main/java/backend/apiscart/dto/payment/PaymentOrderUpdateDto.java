package backend.apiscart.dto.payment;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class PaymentOrderUpdateDto {
	@Min(value = 1, message = "message.request.id.size")
    private Long id;
	
	@Min(value = 1, message = "message.request.id.size")
    private Long idOrder;
	
	@NotBlank(message = "message.request.blank")
	@NotNull(message = "message.request.null")
	@Size(max=50, message = "message.request.paymentorder.size")
	private String names;
	
	@NotBlank(message = "message.request.blank")
	@NotNull(message = "message.request.null")
	@Size(max=50, message = "message.request.paymentorder.size")
	private String surnames;
	
	@NotBlank(message = "message.request.blank")
	@NotNull(message = "message.request.null")
	@Size(max=100, message = "message.request.email.size")
	private String email;
	
	@NotBlank(message = "message.request.blank")
	@NotNull(message = "message.request.null")
	@Size(max=20, message = "message.request.phone.size")
	private String phone;
	
	@NotBlank(message = "message.request.blank")
	@NotNull(message = "message.request.null")
	@Size(max=30, message = "message.request.numbercard.size")
	private String number_card;
}
