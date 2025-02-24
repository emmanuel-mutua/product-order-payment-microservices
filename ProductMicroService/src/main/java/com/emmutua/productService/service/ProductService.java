package com.emmutua.productService.service;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductProjection;
import com.emmutua.productService.model.ProductRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProductService {
    Mono<CreationResponse> createProduct(ProductRequest product);

    Mono<ProductProjection> getProduct(Long requestId);

    Flux<Product> getAllProducts();

    Mono<CreationResponse> reduceQuantity(Long productId, Long quantity);

    Mono<CreationResponse> increaseQuantity(Long productId, Long quantity);
}
