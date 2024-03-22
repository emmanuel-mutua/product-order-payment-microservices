package com.emmutua.orderservice.service;

import com.emmutua.orderservice.model.OrderRequest;

public interface OrderService {
    long placeOrder(OrderRequest orderRequest);
}
