package backend.apiscart.dto.products;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class RequestGetOneDto {
	@NotNull(message = "message.request.idproducto.null")
	@Min(value = 1, message = "message.request.idproducto.min")
	private Long idProducto;
}
