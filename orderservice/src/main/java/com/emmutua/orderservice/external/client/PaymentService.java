package com.emmutua.orderservice.external.client;

import com.emmutua.orderservice.external.request.PaymentRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

@FeignClient(name = "paymentservice/payment")
public interface PaymentService {
    @PostMapping
    Mono<Void> doPayment(@RequestBody PaymentRequest paymentRequest);
}
