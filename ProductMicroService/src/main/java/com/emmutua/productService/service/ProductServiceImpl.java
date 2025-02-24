package com.emmutua.productService.service;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.exception.CustomException;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductProjection;
import com.emmutua.productService.model.ProductRequest;
import com.emmutua.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final TransactionalOperator trxOp;

    @Override
    public Mono<CreationResponse> createProduct(ProductRequest productRequest) {
        Product product = toProduct(productRequest);
        return productRepository.save(product)
                .map(
                        savedProduct -> new CreationResponse(savedProduct.getProductId(), "Product created successfully"))
                .onErrorMap(e -> new CustomException(e.getMessage(), "", HttpStatus.SC_BAD_REQUEST));
    }

    @Override
    public Mono<ProductProjection> getProduct(Long requestId) {
        return productRepository.getProductProjection(requestId);
    }

    @Override
    public Flux<Product> getAllProducts() {
        try {
            return productRepository.findAll();
        }catch (Exception e){
            log.error(e);
            return Flux.empty();
        }
    }

    @Override
    public Mono<CreationResponse> reduceQuantity(Long productId, Long quantity) {
        return getProductFromRepo(productId)
                .switchIfEmpty(Mono.error(new CustomException("Product not found", "", HttpStatus.SC_NOT_FOUND)))
                .flatMap(product -> {
                    product.setProductQuantity(product.getProductQuantity() - quantity);
                    return productRepository.save(product)
                            .map(p -> new CreationResponse(p.getProductId(), "Product quantity reduced"));
                })
                .onErrorMap(e -> new CustomException(e.getMessage(), "", HttpStatus.SC_BAD_REQUEST));
    }

    //if transactional achieve atomicity
    @Transactional
    @Override
    public Mono<CreationResponse> increaseQuantity(Long productId, Long quantity) {
        return getProductFromRepo(productId)
                .switchIfEmpty(Mono.error(new CustomException("Product not found", "", HttpStatus.SC_NOT_FOUND)))
                .flatMap(product -> {
                    product.setProductQuantity(product.getProductQuantity() + quantity);
                    return productRepository.save(product)
                            .map(p -> new CreationResponse(p.getProductId(), "Product quantity increased"));
                })
                .onErrorMap(e -> new CustomException(e.getMessage(), "", HttpStatus.SC_BAD_REQUEST));
                //.then().as(trxOp::transactional);
    }

    Mono<Product> getProductFromRepo(Long requestId) {
        return productRepository.findById(requestId);
    }

    public Product toProduct(ProductRequest productRequest) {
        return Product.builder().productName(productRequest.getProductName()).productDescription(productRequest.getProductDescription()).productQuantity(productRequest.getProductQuantity()).productCategory(productRequest.getProductCategory()).productPrice(productRequest.getProductPrice()).productImage("https/image").build();
    }
}


