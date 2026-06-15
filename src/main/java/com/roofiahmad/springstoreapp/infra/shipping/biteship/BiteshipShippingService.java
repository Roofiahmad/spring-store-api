package com.roofiahmad.springstoreapp.infra.shipping.biteship;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BiteshipShippingService {
    private final WebClient webClient;

    public BiteshipShippingService(
            WebClient.Builder webClientBuilder,
            @Value("${biteship.api.base-url}") String baseUrl,
            @Value("${biteship.api.secret-key}") String secretKey) {

        this.webClient = webClientBuilder
                .baseUrl(baseUrl)
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + secretKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public BiteshipRateResponse getShippingRates(BiteshipRateRequest requestPayload) {
        return this.webClient.post()
                .uri("/rates/couriers")
                .body(BodyInserters.fromValue(requestPayload))
                .retrieve()
                .bodyToMono(BiteshipRateResponse.class)
                .block();
    }
}
