package com.emmutua.paymentService.service;

import com.emmutua.paymentService.model.PaymentRequest;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);
}
