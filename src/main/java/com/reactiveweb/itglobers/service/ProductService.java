package com.reactiveweb.itglobers.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reactiveweb.itglobers.model.Product;
import com.reactiveweb.itglobers.repository.ProductRepository;

import reactor.core.publisher.Mono;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DiscountService discountService;

    @Autowired
    private ProductStatusService productStatusService;

    // Obtener producto por ID
    public Mono<Product> getProductById(String id) {
        return productRepository.findById(id)
            .flatMap(product -> {
                // Obtener el descuento del servicio externo
                return discountService.getDiscountByProductId(product.getProductId())
                    .flatMap(discount -> {
                        product.setDiscount(discount);
                        product.setFinalPrice(product.getPrice() * (100 - product.getDiscount()) / 100);

                        // Obtener el nombre del estado desde el caché
                        product.setStatusName(productStatusService.getStatusName(product.getStatus()));
                        return Mono.just(product);
                    });
            });
    }

    // Crear un nuevo producto
    public Mono<Product> createProduct(Product product) {
        return productRepository.save(product);
    }

    // Actualizar un producto existente
    public Mono<Product> updateProduct(String id, Product updatedProduct) {
        return productRepository.findById(id)
            .flatMap(existingProduct -> {
                // Actualiza los campos que necesitas
                existingProduct.setName(updatedProduct.getName());
                existingProduct.setStatus(updatedProduct.getStatus());
                existingProduct.setStock(updatedProduct.getStock());
                existingProduct.setDescription(updatedProduct.getDescription());
                existingProduct.setPrice(updatedProduct.getPrice());

                return productRepository.save(existingProduct);
            });
    }
}