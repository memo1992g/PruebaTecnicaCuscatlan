package backend.apiscart.dto.base;

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
public class GenericResponseServiceDto <T>{
	private T item;
	private Boolean success;
	private String itemString;
	private String message;
}
