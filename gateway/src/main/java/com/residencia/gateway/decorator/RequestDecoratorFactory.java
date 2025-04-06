package com.residencia.gateway.decorator;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.residencia.gateway.model.GatewayRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class RequestDecoratorFactory {

    private final ObjectMapper objectMapper;
    public ServerHttpRequestDecorator getDecorator(GatewayRequest request) {
        return switch (request.getTargetMethod().name()) {
            case "GET" -> new GetRequestDecorator(request);
            case "POST" -> new PostRequestDecorator(request, objectMapper);
            case "PUT" -> new PutRequestDecorator(request, objectMapper);
            case "DELETE" -> new DeleteRequestDecorator(request);
            default -> throw new IllegalArgumentException("Invalid http method");
        };
    }
}