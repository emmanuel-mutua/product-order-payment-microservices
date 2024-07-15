package com.emmutua.orderservice.controller;

import com.emmutua.orderservice.entity.Order;
import com.emmutua.orderservice.model.OrderRequest;
import com.emmutua.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @ResponseBody means object returned is automatically serialized into JSON and passed back into the HttpResponse object.
 */

@RestController
@RequestMapping("/order")
@Log4j2
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    @PostMapping("/placeOrder")
    public ResponseEntity<Long> placeOrder(@RequestBody OrderRequest orderRequest){
        long orderId = orderService.placeOrder(orderRequest);
        log.info("orderid: " + orderId);
        return new ResponseEntity<>(orderId, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Order>> getOrders(){
        var response = orderService.findAll();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
