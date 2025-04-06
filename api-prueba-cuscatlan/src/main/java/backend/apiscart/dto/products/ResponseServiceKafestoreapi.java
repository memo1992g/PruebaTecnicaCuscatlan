package backend.apiscart.dto.products;

import java.util.List;

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
public class ResponseServiceKafestoreapi {

	private Boolean success;
	private String message;
	private List<ResponseGetKafestoreapiDto> item;

}
