package com.emmutua.paymentService.controller;

import com.emmutua.paymentService.model.PaymentRequest;
import com.emmutua.paymentService.paypal.PaypalService;
import com.emmutua.paymentService.service.PaymentService;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private static final Logger log = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    private final PaypalService paypalService;

    @GetMapping("/paypal")
    public String paypalHome(){
            return "index";
    }
//    @PostMapping("/paypal/create")
//    public RedirectView createPaypalPayment(){
//        try {
//            String cancelUrl = "";
//            String successUrl = "";
//            Payment payment = paypalService.createPayment(
//                    10.0,
//                    "USD",
//                    "paypal",
//                    "sale",
//                    "Payment desc",
//                    cancelUrl,
//                    successUrl
//            );
//
//        }catch (PayPalRESTException e){
//            log.error("Error occurred::{}" ,e.getMessage());
//        }
//    }
    @PostMapping
    public ResponseEntity<Long> doPayment(@RequestBody PaymentRequest paymentRequest){
        return new ResponseEntity<>(
                paymentService.doPayment(paymentRequest), HttpStatus.OK
        );
    }
}
