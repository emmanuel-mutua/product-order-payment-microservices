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
    public long placeOrder(OrderRequest orderRequest) {
        log.info("Placing order: " + orderRequest);
        // save the data with the status of the order created
        // reduce the quantity, call api, can use feign client, easily without calling url etc
        // payment service to do payment
        // save the status of the order
        //rest api call using the OpenFeign client
        reduceQuantityFromProducts(orderRequest);
        log.info("Creating order with status created: " + orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Calling the payment service to complete payment");
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .orderId(order.getOrderId())
                .amount(orderRequest.getTotalAmount())
                .paymentMode(orderRequest.getPaymentMode())
                .build();
        String orderStatus = null;
        try {
            doPayment(paymentRequest);
            orderStatus = "PLACED";
        } catch (Exception e) {
            log.error("Error occurred in payment: Message: {}", e.getMessage());
            orderStatus = "PAYMENT_FAILED";
        }
        order.setOrderStatus(orderStatus);
        orderRepository.save(order);
        log.info("Order placed success with order id" + order.getOrderId());
        return order.getOrderId();
    }
    @CircuitBreaker(name = "doPayment", fallbackMethod = "doPaymentFallBack")
    private void doPayment(PaymentRequest paymentRequest) {
        paymentService.doPayment(paymentRequest);
    }

    private void doPaymentFallBack(Throwable throwable){
        log.error("Error occurred in error occurred while paying: Message: {}", throwable.getMessage());
        throw new CustomException(throwable.getLocalizedMessage(),"500", 500);
    }

    @CircuitBreaker(name = "reduceQuantityFromProducts", fallbackMethod = "reduceQuantityFromProductsFallBack")
    private void reduceQuantityFromProducts(OrderRequest orderRequest) {
        log.info("Reducing quantity from products: quantity {}",orderRequest.getQuantity());
                 productService.reduceQuantity(orderRequest.getProductId(), orderRequest.getQuantity());
    }

    private void reduceQuantityFromProductsFallBack(Throwable throwable){
        log.error("Error occurred in reduceQuantityFromProductsFallBack: Message: {}", throwable.getMessage());
        throw new CustomException(throwable.getLocalizedMessage(),"500", 500);
    }

    @Override
    public List<Order> findAll() {
        return orderRepository.findAll();
    }
}
