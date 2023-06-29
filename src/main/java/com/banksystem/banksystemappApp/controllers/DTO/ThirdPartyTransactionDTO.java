package com.banksystem.banksystemappApp.controllers.DTO;

import java.math.BigDecimal;

public class ThirdPartyTransactionDTO {

    private BigDecimal amount;

    private String accountNumber;

    public ThirdPartyTransactionDTO(BigDecimal amount, String accountNumber) {
        this.amount = amount;
        this.accountNumber = accountNumber;

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

}
