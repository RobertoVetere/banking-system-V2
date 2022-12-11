package com.banksystem.banksystemappApp.controllers.DTO;

import java.math.BigDecimal;

public class ThirdPartyTransactionDTO {

    private BigDecimal amount;

    private String accountNumber;

    private String secretKey;

    public ThirdPartyTransactionDTO(BigDecimal amount, String accountNumber, String secretKey) {
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.secretKey = secretKey;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
