package backend.apiscart.service.consumer;



import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeoutException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import backend.apiscart.component.exception.GenericException;
import backend.apiscart.component.util.log.LogUtil;
import backend.apiscart.component.util.log.LogUtil.TYPELOG;
import backend.apiscart.dto.products.ResponseGetKafestoreapiDto;
import io.netty.handler.timeout.WriteTimeoutException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ConsumerImpl<T> implements Consumer {
	
	@Value("${app.conf.rest.timeout}")
	private Duration restTimeOut;
    
    private WebClient.Builder builder;
    private Map<String,String> headers;
    private LogUtil log;

    public ConsumerImpl(WebClient.Builder builder, LogUtil log) {
        this.builder = builder;
        this.log = log;
    }

    
    public WebClient initializer(String baseUrl){ 

        Map<String,String> headerHelper = getDefaultHeaders();          

        return builder.baseUrl(baseUrl).defaultHeaders(defaultHeaders -> {
            for (var entry : headerHelper.entrySet()) {
                defaultHeaders.set(entry.getKey(), entry.getValue());
            }
        }).build();
    }

   
    public <W,T> Mono<T> postOneByBody(W request, String baseUrl, String url, Class<T> response){
        
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .bodyToMono(response).timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","error.time.to.create", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
        
    }
  
    public <W,T> Flux<T> postAnyByBody(W request, String baseUrl, String url, Class<T> response){
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromValue(request))
            .retrieve()
            .onStatus(HttpStatus::isError, resp -> resp.createException())
            .bodyToFlux(response);
    }


    public <T> Mono<T> getOneByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response){
        
        WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> uriBuilder
            .path(url)
            .queryParams(request)
            .build()
            ).retrieve().bodyToMono(response);
    }

 
    public <T> Flux<T> getAnyByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response){
        WebClient client = initializer(baseUrl);
        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .queryParams(request)
                .build()
            ).retrieve()
            .bodyToFlux(response);
    }

 
    public <T> Mono<T> getOneByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments){
        
        WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build(arguments)
            ).retrieve()
            .bodyToMono(response)
            .timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","generic.consumer.error.timeout", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
    }

    public <T> Flux<T> getAnyByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments){
        WebClient client = initializer(baseUrl);
        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build(arguments)
            ).retrieve()
            .bodyToFlux(response);
    }

    public void setDefaultHeaders(Map<String,String> headers){
        this.headers = headers;
    }


    private Map<String,String> getDefaultHeaders() {
        Map<String,String> defaultHeader = new HashMap<>();
        defaultHeader.put("Content-Type", "application/json");
        defaultHeader.put("Accept", "*");

        if (headers == null || headers.isEmpty())
            return defaultHeader;
        else
            return headers;
    }

    @Override
    public <T> Mono<T> postOneByFormBody(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response) {
        WebClient client = initializer(baseUrl);
        return client.post().uri(url)
            .body(BodyInserters.fromFormData(request))
            .retrieve()
            .bodyToMono(response);
    }


	@Override
	public Mono<List<ResponseGetKafestoreapiDto>> getOneByQueryStringList(String baseUrl, String url) {
		WebClient client = initializer(baseUrl);
		return client.get()
                .uri(url)
                .retrieve()
                .bodyToFlux(ResponseGetKafestoreapiDto.class)
                .collectList();
	}


	@Override
	public <T> Mono<T> getOneByQueryStringOne(String baseUrl, String url, Class<T> response) {
		WebClient client = initializer(baseUrl);

        return client.get().uri(uriBuilder -> 
                uriBuilder
                .path(url)
                .build()
            ).retrieve()
            .bodyToMono(response)
            .timeout(restTimeOut)
            .onErrorMap(TimeoutException.class, ex -> {
                List<String> errors = new ArrayList<>();
                errors.add(ex.getMessage());
                errors.add(ex.getLocalizedMessage());

                return new GenericException(HttpStatus.REQUEST_TIMEOUT,
                    "Generic Consumer","generic.consumer.error.timeout", errors);

            })
            .doOnError(WriteTimeoutException.class, ex -> log.write(TYPELOG.ERROR, "TimeOutError", ex));
	}


}