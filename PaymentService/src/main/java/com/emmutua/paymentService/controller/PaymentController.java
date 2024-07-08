package com.emmutua.paymentService.controller;

import com.emmutua.paymentService.entity.TransactionDetails;
import com.emmutua.paymentService.model.PaymentRequest;
import com.emmutua.paymentService.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<TransactionDetails>> getTransactions(){
        var response = paymentService.getTransactions();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

}
