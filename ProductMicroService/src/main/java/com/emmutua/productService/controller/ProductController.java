package com.emmutua.productService.controller;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductRequest;
import com.emmutua.productService.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;
    @PostMapping
    public ResponseEntity<CreationResponse> createProduct(@RequestBody ProductRequest product) {
        var response = productService.createProduct(product);
        return ResponseEntity.ok(response);
    }
    @GetMapping("/{requestId}")
    public ResponseEntity<Product> getProduct(@PathVariable Long requestId) {
        var response = productService.getProduct(requestId);
        return ResponseEntity.ok(response);
    }
    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        var response = productService.getAllProducts();
        return ResponseEntity.ok(response);
    }
    @PutMapping("/reduceQuantity/{id}")
    ResponseEntity<Void> reduceQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity
    ){
        var response = productService.reduceQuantity(productId, quantity);
        return ResponseEntity.ok(response);
    }
    @PutMapping("/increaseQuantity/{id}")
    ResponseEntity<CreationResponse> increaseQuantity(
            @PathVariable("id") Long productId,
            @RequestParam Long quantity
    ){
        var response = productService.increaseQuantity(productId, quantity);
        return ResponseEntity.ok(response);
    }
}
