package backend.apiscart.component.util.log;

import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LogUtilImpl implements LogUtil {
    
    private Logger log;
    
    public LogUtilImpl(){
        log = LoggerFactory.getLogger(LogUtilImpl.class);
    }

    public <T> LogUtilImpl(Class<T> name){
        log = LoggerFactory.getLogger(name);
    }

    public boolean write(TYPELOG typeLog, String message, Object... arguments) {
        ObjectMapper mapper = new ObjectMapper();
        
        try {
		    mapper.setVisibility(PropertyAccessor.ALL, Visibility.ANY);
            mapper.findAndRegisterModules();

            String object = mapper.writeValueAsString(arguments);
            switch(typeLog){
                case DEBUG:
                    log.warn(object, message);
                    break;
                case WARNING:
                    log.warn(object, message);
                    break;
                case ERROR:
                    log.error(object, message);
                    break;
                default:
                    log.info(object, message);
            }
        }catch(Exception ex){
            log.error(message, ex);
            return false;
        }
        
        return true;
    }
    
}
