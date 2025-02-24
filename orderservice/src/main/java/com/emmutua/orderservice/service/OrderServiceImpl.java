package com.emmutua.orderservice.service;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.exception.CustomException;
import com.emmutua.orderservice.external.client.PaymentService;
import com.emmutua.orderservice.external.client.ProductService;
import com.emmutua.orderservice.external.request.PaymentRequest;
import com.emmutua.orderservice.model.OrderRequest;
import com.emmutua.orderservice.repository.OrderRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.List;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;
    private final PaymentService paymentService;
//    private final CircuitBreakerFactory circuitBreakerFactory;
//    CircuitBreaker circuitBreaker = circuitBreakerFactory.create("orderService_circuitBreaker");

    @Override
    public Mono<Long> placeOrder(OrderRequest orderRequest) {
        log.info("Placing order: {}", orderRequest);

        // Create order object with status CREATED
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();

        return reduceQuantityFromProducts(orderRequest)
                .then(orderRepository.save(order))
                .flatMap(savedOrder -> {
                    log.info("Calling payment service for order {}", savedOrder.getOrderId());

                    PaymentRequest paymentRequest = PaymentRequest.builder()
                            .orderId(savedOrder.getOrderId())
                            .amount(orderRequest.getTotalAmount())
                            .paymentMode(orderRequest.getPaymentMode())
                            .build();

                    return doPayment(paymentRequest)
                            .thenReturn("PLACED")
                            .onErrorReturn("PAYMENT_FAILED")
                            .flatMap(status -> {
                                savedOrder.setOrderStatus(status);
                                return orderRepository.save(savedOrder);
                            })
                            .map(orderObj -> orderObj.getOrderId());
                });
    }


    @CircuitBreaker(name = "doPayment", fallbackMethod = "doPaymentFallBack")
    private Mono<Void> doPayment(PaymentRequest paymentRequest) {
        return paymentService.doPayment(paymentRequest);
    }

    private void doPaymentFallBack(PaymentRequest paymentRequest, Throwable throwable) {
        log.error("Payment failed for orderId: {}: Message: {}", paymentRequest.getOrderId(), throwable.getMessage());
        throw new CustomException("Payment service unavailable. Please try again later.", "500", 500);
    }


    @CircuitBreaker(name = "reduceQuantityFromProducts", fallbackMethod = "reduceQuantityFromProductsFallBack")
    private Mono<Void> reduceQuantityFromProducts(OrderRequest orderRequest) {
        log.info("Reducing quantity from products: quantity {}", orderRequest.getQuantity());
        return
                productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
    }

    private Mono<Void> reduceQuantityFromProductsFallBack(Throwable throwable) {
        log.error("Error occurred in reduceQuantityFromProductsFallBack: Message: {}", throwable.getMessage());
        throw new CustomException(throwable.getLocalizedMessage(), "500", 500);
    }

    @Override
    public Flux<Order> findAll() {
        return orderRepository.findAll();
    }
}
