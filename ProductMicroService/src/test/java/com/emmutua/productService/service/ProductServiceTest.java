package com.emmutua.productService.service;

import com.emmutua.productService.entity.Product;
import com.emmutua.productService.model.ProductRequest;
import com.emmutua.productService.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@ExtendWith(MockitoExtension.class) //integrate mockito in JUnit (open mocks)
class ProductServiceTest {

    @Mock
    ProductRepository productRepository;

    @InjectMocks
    ProductServiceImpl productService;

    @Test
    void verify_response_not_null_and_creation_message_is_equal_to_specified_one() {
        var product = Product.builder()
                .productCategory("")
                .productDescription("")
                .productName("")
                .productPrice(220.0)
                .productStatus("")
                .build();

        var productReq = ProductRequest.builder().build();

        when(productRepository.save(any(Product.class))).thenReturn(product);

        var response = productService.createProduct(productReq);
        assertNotNull(response);
        assertEquals("Product created successfully", response.getCreationMessage());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void getProduct() {
        var product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        verify(productRepository, times(1)).findById(1L);

        var response = productService.getProduct(1L);
        assertNotNull(response);
        assertEquals(product, response);
    }

    @Test
    void getAllProducts() {
        var products = List.of(new Product(), new Product());
        when(productService.getAllProducts()).thenReturn(products);
        var response = productService.getAllProducts();

        assertEquals(products, response);
        Mockito.verify(productRepository, Mockito.times(1)).findAll();
    }

    @Test
    void reduceQuantity() {
    }

    @Test
    void increaseQuantity() {
    }
}

/**
 * Servlet -> component that can handle requests and responses
 * Servlet env -> ecosystem where java servlets run
 * mock servlet env-> fake servlet env / simulates the real env
 */