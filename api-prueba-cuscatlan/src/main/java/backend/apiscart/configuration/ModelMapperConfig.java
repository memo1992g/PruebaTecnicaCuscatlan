package backend.apiscart.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        return customMapping(mapper);
    }

    private ModelMapper customMapping(ModelMapper modelMapper){
    	
        return modelMapper;      
    }
}
