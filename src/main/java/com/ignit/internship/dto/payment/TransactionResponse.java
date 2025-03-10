package com.ignit.internship.dto.payment;

public class TransactionResponse {

    private String transactionToken;

    public TransactionResponse(String transactionToken) {
        this.transactionToken = transactionToken;
    }

    public String gettransactionToken() {
        return transactionToken;
    }
}
