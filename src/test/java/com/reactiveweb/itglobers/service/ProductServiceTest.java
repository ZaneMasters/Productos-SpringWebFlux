package com.reactiveweb.itglobers.service;

import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.reactiveweb.itglobers.model.Product;
import com.reactiveweb.itglobers.repository.ProductRepository;

import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private DiscountService discountService;

    @Mock
    private ProductStatusService productStatusService;

    @InjectMocks
    private ProductService productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetProductById() {
        // Preparar datos de prueba
        String productId = "1";
        Product product = new Product(productId, "Product A", 1, 100, "Description", 100.0, productId, null, null);
        Double discount = 10.0;
        String statusName = "Active";

        // Configurar el comportamiento de los mocks
        when(productRepository.findById(productId)).thenReturn(Mono.just(product));
        when(discountService.getDiscountByProductId(productId)).thenReturn(Mono.just(discount));
        when(productStatusService.getStatusName(1)).thenReturn(statusName);

        // Ejecutar la prueba
        Mono<Product> result = productService.getProductById(productId);

        // Verificar los resultados con StepVerifier
        StepVerifier.create(result)
                .assertNext(p -> {
                    assertEquals(productId, p.getProductId());
                    assertEquals("Product A", p.getName());
                    assertEquals(1, p.getStatus());
                    assertEquals(100.0, p.getPrice());
                    assertEquals(10.0, p.getDiscount());
                    assertEquals(90.0, p.getFinalPrice());
                    assertEquals("Active", p.getStatusName());
                })
                .verifyComplete();
    }

    @Test
    public void testCreateProduct() {
        // Preparar datos de prueba
        Product product = new Product(null, "Product A", 1, 100, "Description", 100.0, null, null, null);

        // Configurar el comportamiento de los mocks
        when(productRepository.save(product)).thenReturn(Mono.just(product));

        // Ejecutar la prueba
        Mono<Product> result = productService.createProduct(product);

        // Verificar los resultados
        StepVerifier.create(result)
                .expectNext(product)
                .verifyComplete();
    }
}
