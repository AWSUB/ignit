package com.ignit.internship.dto.payment;

import com.fasterxml.jackson.annotation.JsonAlias;

public class PaymentNotificationRequest {

    @JsonAlias("transaction_status")    
    private String transactionStatus;

    @JsonAlias("status_code")
    private String statusCode;

    @JsonAlias("signature_key")
    private String signatureKey;

    @JsonAlias("order_id")
    private String orderId;

    @JsonAlias("gross_amount")
    private String grossAmount;

    @JsonAlias("fraud_status")
    private String fraudStatus;

    public PaymentNotificationRequest(
        String transactionStatus, 
        String statusCode, 
        String signatureKey, 
        String orderId,
        String grossAmount, 
        String fraudStatus
    ) {
        this.transactionStatus = transactionStatus;
        this.statusCode = statusCode;
        this.signatureKey = signatureKey;
        this.orderId = orderId;
        this.grossAmount = grossAmount;
        this.fraudStatus = fraudStatus;
    }

    public String getTransactionStatus() {
        return transactionStatus;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public String getSignatureKey() {
        return signatureKey;
    }

    public String getOrderId() {
        return orderId;
    }

    public String getGrossAmount() {
        return grossAmount;
    }

    public String getFraudStatus() {
        return fraudStatus;
    }

    
}
