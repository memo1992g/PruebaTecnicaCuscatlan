package backend.apiscart.configuration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OpenApiProperties {
	private String projectTitle;
	private String projectDescription;
	private  String projectVersion;

}
