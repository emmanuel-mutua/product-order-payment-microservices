package com.emmutua.orderservice.external.request;

import com.emmutua.orderservice.model.PaymentMode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor //default const
@Builder
public class PaymentRequest {
    private Long orderId;
    private long amount;
    private String referenceNumber;
    private PaymentMode paymentMode;
}
