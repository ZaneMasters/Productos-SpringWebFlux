package com.reactiveweb.itglobers.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@Service
public class DiscountService {

    private final WebClient webClient;

    public DiscountService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://66e9ab4e87e41760944a593e.mockapi.io/api/v1/discountproduct").build();
    }

    public Mono<Double> getDiscountByProductId(String productId) {
        return webClient.get()
                .uri("/discounts/{id}", productId)
                .retrieve()
                .bodyToMono(DiscountResponse.class)
                .map(DiscountResponse::getDiscount)
                .defaultIfEmpty(0.0); // Si no hay descuento, devolver 0
    }

    // Clase interna para mapear la respuesta de la API externa
    private static class DiscountResponse {
        private Double discount;

        public Double getDiscount() {
            return discount;
        }

        public void setDiscount(Double discount) {
            this.discount = discount;
        }
    }
}