package backend.apiscart.dto.products;

import java.math.BigDecimal;

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
public class ResponseGetKafestoreapiDto {
	private int id;
	private String title;
	private BigDecimal price;
	private String description;
	private String category;
	private String image;
	private Rating rating;
}
