package com.emmutua.productService.service;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.exception.CustomException;
import com.emmutua.productService.model.CreationResponse;
import com.emmutua.productService.model.ProductRequest;
import com.emmutua.productService.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
@Log4j2
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService{
    private final ProductRepository productRepository;
    @Override
    public CreationResponse createProduct(ProductRequest productRequest) {
        try {
            Product product = toProduct(productRequest);
             product = productRepository.save(product);
             return new CreationResponse(
               product.getProductId(), "Product created successfully"
             );
        }catch (Exception e){
            throw new CustomException(e.getMessage(),"", HttpStatus.SC_BAD_REQUEST);
        }
    }

    @Override
    public Product getProduct(Long requestId) {
        return getProductFromRepo(requestId);
    }

        @Override
        public List<Product> getAllProducts() {
            return productRepository.findAll();
        }

    @Override
    public Void reduceQuantity(Long productId, Long quantity) {
        Product product = getProductFromRepo(productId);
        if (product.getProductQuantity() >= quantity) {
            product.setProductQuantity(product.getProductQuantity() - quantity);
        }else {
            throw new CustomException("No enough quantity to reduce","", HttpStatus.SC_BAD_REQUEST);
        }
        log.info("Product reduced quantity successfully");
        return null;
    }

    @Override
    public CreationResponse increaseQuantity(Long productId, Long quantity) {
        Product product = getProductFromRepo(productId);
            product.setProductQuantity(product.getProductQuantity() - quantity);
        return new CreationResponse(
                productId,
                "Product reduced quantity successfully"
        );

    }

    Product getProductFromRepo(Long requestId){
       return productRepository.findById(requestId).orElseThrow(
                () -> new CustomException("Product with specified id not found","",HttpStatus.SC_BAD_REQUEST)
        );
    }
    public Product toProduct(ProductRequest productRequest) {
       return
               Product.builder()
                .productName(productRequest.getProductName())
                .productDescription(productRequest.getProductDescription())
                .productQuantity(productRequest.getProductQuantity())
                .productCategory(productRequest.getProductCategory())
                .productPrice(productRequest.getProductPrice())
                .productImage("https/image")
                .build();
    }
}


