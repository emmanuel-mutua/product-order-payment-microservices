package com.emmutua.productService.controller;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductProjection;
import com.emmutua.productService.model.ProductRequest;
import com.emmutua.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;


    @PostMapping
    public Mono<ResponseEntity<CreationResponse>> createProduct(@RequestBody ProductRequest product) {
        return productService.createProduct(product)
                .map(ResponseEntity::ok);
    }

    @GetMapping("/{requestId}")
    public Mono<ResponseEntity<ProductProjection>> getProduct(@PathVariable Long requestId) {
        return productService.getProduct(requestId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<Flux<Product>> getAllProducts() {
        var products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PutMapping("/reduceQuantity/{id}")
    public Mono<ResponseEntity<CreationResponse>> reduceQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity
    ) {
        return productService.reduceQuantity(productId, quantity)
                .map(ResponseEntity::ok);
    }

    @PutMapping("/increaseQuantity/{id}")
    public Mono<ResponseEntity<CreationResponse>> increaseQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity
    ) {
        return productService.increaseQuantity(productId, quantity)
                .map(ResponseEntity::ok);
    }
}
