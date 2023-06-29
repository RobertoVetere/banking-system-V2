package com.banksystem.banksystemappApp.controllers.DTO;

import java.math.BigDecimal;

public class TransactionDTO {

    private String transactionOwnerAccountNumber;

    private String targetAccountNumber;

    private String targetOwnerName;

    private BigDecimal amount;

    public TransactionDTO(String transactionOwnerAccountNumber, String targetAccountNumber, String targetOwnerName, BigDecimal amount) {
        this.transactionOwnerAccountNumber = transactionOwnerAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.targetOwnerName = targetOwnerName;
        this.amount = amount;
    }

    public String getTransactionOwnerAccountNumber() {
        return transactionOwnerAccountNumber;
    }

    public void setTransactionOwnerAccountNumber(String transactionOwnerAccountNumber) {
        this.transactionOwnerAccountNumber = transactionOwnerAccountNumber;
    }

    public String getTargetAccountNumber() {
        return targetAccountNumber;
    }

    public void setTargetAccountNumber(String targetAccountNumber) {
        this.targetAccountNumber = targetAccountNumber;
    }

    public String getTargetOwnerName() {
        return targetOwnerName;
    }

    public void setTargetOwnerName(String targetOwnerName) {
        this.targetOwnerName = targetOwnerName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
}
