package com.emmutua.paymentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "TRANSACTION_DETAILS")
public class TransactionDetails {
    @Id
    @GeneratedValue
    private long id;
    @Column(name = "PAYMENT_MODE")
    private String paymentMode;
    @Column(name = "REFERENCE_NUMBER")
    private String referenceNumber;
    @Column(name = "PAYMENT_STATUS")
    private String paymentStatus;
    @Column(name = "PAYMENT_DATE")
    private Instant paymentDate;
    @Column(name = "AMOUNT")
    private long amount;
    @Column(name = "ORDER_ID")
    private long orderId;
}
