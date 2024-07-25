package com.emmutua.orderservice.service;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.external.client.PaymentService;
import com.emmutua.orderservice.external.client.ProductService;
import com.emmutua.orderservice.external.request.PaymentRequest;
import com.emmutua.orderservice.model.OrderRequest;
import com.emmutua.orderservice.model.PaymentMode;
import com.emmutua.orderservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) //ExtendWith is used in JUnit to integrate Mockito, enable annotations and initialize
class OrderServiceTest {

    @Mock
    OrderRepository orderRepository;
    @Mock
    ProductService productService;
    @Mock
    PaymentService paymentService;


    @InjectMocks
    OrderServiceImpl orderService;

//    @BeforeEach
//    void setUp() {
//        //enable mockito annotations
//        MockitoAnnotations.openMocks(this);
//    }


    @Test
    void placeOrder() {
        // Arrange
        OrderRequest orderRequest = OrderRequest.builder()
                .productId(1L)
                .quantity(10)
                .totalAmount(100)
                .paymentMode(PaymentMode.CREDIT_CARD)
                .build();

        Order order = Order.builder()
                .orderId(1L)
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        doReturn(order).when(orderRepository).save(any(Order.class));
        long orderID = orderService.placeOrder(orderRequest);
        assertEquals(orderID, 1L );
        //verify this happens once
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(productService).reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        verify(paymentService).doPayment(any(PaymentRequest.class));
    }

    @Test
    void placeOrder_PaymentFailure() {
        // Arrange
        OrderRequest orderRequest = OrderRequest.builder()
                .productId(1L)
                .quantity(10)
                .totalAmount(100)
                .paymentMode(PaymentMode.CREDIT_CARD)
                .build();

        Order order = Order.builder()
                .orderId(1L)
                .amount(100)
                .orderStatus("CREATED")
                .productId(1L)
                .orderDate(Instant.now())
                .quantity(10)
                .build();

        doReturn(order).when(orderRepository.save(any(Order.class)));
        doThrow(new RuntimeException("Payment failed")).when(paymentService).doPayment(any(PaymentRequest.class));

        // Act
        long orderId = orderService.placeOrder(orderRequest);

        // Assert
        assertEquals(1L, orderId);
        verify(orderRepository, times(2)).save(any(Order.class));
        verify(productService).reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
        verify(paymentService).doPayment(any(PaymentRequest.class));
    }

    @Test
    void findAll() {
    }
}