package com.emmutua.paymentService.service;

import com.emmutua.paymentService.entity.TransactionDetails;
import com.emmutua.paymentService.model.PaymentRequest;

import java.util.List;

public interface PaymentService {
    Long doPayment(PaymentRequest paymentRequest);

    List<TransactionDetails> getTransactions();
}
