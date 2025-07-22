package com.emmutua.productService.service;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductRequest;

import java.util.List;

public interface ProductService {
    CreationResponse createProduct(ProductRequest product);

    Product getProduct(Long requestId);

    List<Product> getAllProducts();

    Void reduceQuantity(Long productId, Long quantity);

    CreationResponse increaseQuantity(Long productId, Long quantity);
}
