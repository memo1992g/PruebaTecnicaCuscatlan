package backend.apiscart.dto.orders;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
public class OrderDetailsUpdateDto {
	
	@Min(value = 1, message = "message.request.id.size")
	private Long id;
	
	@Min(value = 1, message = "message.request.id.size")
	private Long idProduct;
	
	@Min(value = 1, message = "message.request.id.size")
	private int quantity;
	
	@DecimalMin(value = "0.00")
    @NotNull
	private BigDecimal price;
}
