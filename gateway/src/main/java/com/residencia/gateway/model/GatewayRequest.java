package com.residencia.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.server.ServerWebExchange;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GatewayRequest {
    private HttpMethod targetMethod;

    private LinkedMultiValueMap<String, String> queryParams;

    private LinkedMultiValueMap<String, Boolean> queryParamsbool;

    private Object body;

    // Para recibir los headers personalizados del JSON de entrada
    // Para recibir los headers del JSON como un mapa simple
    private Map<String, String> headers;

    @JsonIgnore
    private ServerWebExchange exchange;

    @JsonIgnore
    private HttpHeaders processedHeaders;
}
