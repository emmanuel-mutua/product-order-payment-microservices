package com.emmutua.orderservice.service;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.model.OrderRequest;

import java.util.List;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);

    List<Order> findAll();
}
