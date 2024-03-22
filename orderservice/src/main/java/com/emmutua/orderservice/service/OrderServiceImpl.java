package com.emmutua.orderservice.service;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.model.OrderRequest;
import com.emmutua.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    @Override
    public long placeOrder(OrderRequest orderRequest) {
        // save the data with the status of the order created
        // reduce the quantity, call api, can use feign client, easily without calling url etc
        // payment service to do payment
        // save the status of the order
        log.info("Placing order: " + orderRequest);
        Order order = Order.builder()
                .amount(orderRequest.getTotalAmount())
                .orderStatus("CREATED")
                .productId(orderRequest.getProductId())
                .orderDate(Instant.now())
                .quantity(orderRequest.getQuantity())
                .build();
        order = orderRepository.save(order);
        log.info("Order placed success with order id" +order.getOrderId() );
        return order.getOrderId();
    }
}
