package backend.apiscart;

import java.util.TimeZone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@SpringBootApplication
@Configuration
//@ComponentScan(basePackages = "backend.apiscart.configuration.datasource")
public class ShoppingCart {

	public static void main(String[] args) {
	    TimeZone.setDefault(TimeZone.getTimeZone("GMT-6:00"));
		SpringApplication.run(ShoppingCart.class, args);
	}
}
