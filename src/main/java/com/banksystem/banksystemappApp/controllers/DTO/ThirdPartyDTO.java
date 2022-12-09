package com.banksystem.banksystemappApp.controllers.DTO;

import java.math.BigDecimal;

public class ThirdPartyDTO {

    private BigDecimal amount;

    private String accountNumber;

    private Long secretKey;

    public ThirdPartyDTO(BigDecimal amount, String accountNumber, Long secretKey) {
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

    public Long getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Long secretKey) {
        this.secretKey = secretKey;
    }
}
