package backend.apiscart.service.consumer;

import java.util.List;
import java.util.Map;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import backend.apiscart.dto.products.ResponseGetKafestoreapiDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface Consumer {
    
    <T> Mono<T> postOneByFormBody(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response);

    <W,T> Mono<T> postOneByBody(W request, String baseUrl, String url, Class<T> response);

    <W,T> Flux<T> postAnyByBody(W request, String baseUrl, String url, Class<T> response);

    <T> Mono<T> getOneByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response);
    
    <T> Mono<T> getOneByQueryStringOne(String baseUrl, String url, Class<T> response);
    
    Mono<List<ResponseGetKafestoreapiDto>> getOneByQueryStringList(String baseUrl, String url);

    <T> Flux<T> getAnyByQueryString(MultiValueMap<String,String> request, String baseUrl, String url, Class<T> response);

    <T> Mono<T> getOneByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments);

    <T> Flux<T> getAnyByPathParameter(String baseUrl, String url, Class<T> response, Object... arguments);

    void setDefaultHeaders(Map<String,String> headers);
    
    WebClient initializer(String baseUrl);
 
}