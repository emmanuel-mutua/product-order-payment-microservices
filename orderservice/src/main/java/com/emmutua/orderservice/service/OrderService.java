package com.emmutua.orderservice.service;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.model.OrderRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface OrderService {
    Mono<Long> placeOrder(OrderRequest orderRequest);

    Flux<Order> findAll();
}
