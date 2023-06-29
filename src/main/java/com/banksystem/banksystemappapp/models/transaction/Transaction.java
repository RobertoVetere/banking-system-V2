package com.banksystem.banksystemappapp.models.transaction;

import com.banksystem.banksystemappapp.enums.TransactionType;
import com.banksystem.banksystemappapp.models.accounts.Account;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    private String transactionOwnerAccountNumber;

    private String targetAccountNumber;

    @ManyToOne
    @JoinColumn(name = "transaction_owner")
    private Account transactionOwner;
    private String targetOwnerName;

    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "target_account")
    private Account targetAccount;


    private LocalDate initiationDate = LocalDate.now();
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;


    public Transaction() {
    }

    public Transaction(String transactionOwnerAccountNumber, String targetAccountNumber, Account transactionOwner,
                       Account targetAccount, String targetOwnerName, BigDecimal amount, TransactionType transactionType) {
        this.transactionOwnerAccountNumber = transactionOwnerAccountNumber;
        this.targetAccountNumber = targetAccountNumber;
        this.transactionOwner = transactionOwner;
        this.targetAccount = targetAccount;
        this.targetOwnerName = targetOwnerName;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public Transaction(String transactionOwnerAccountNumber, Account transactionOwner, BigDecimal amount, TransactionType transactionType) {
        this.transactionOwnerAccountNumber = transactionOwnerAccountNumber;
        this.transactionOwner = transactionOwner;
        this.amount = amount;
        this.transactionType = transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
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

    public Long getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(Long transaction_id) {
        this.transaction_id = transaction_id;
    }

    public Account getTransactionOwner() {
        return transactionOwner;
    }

    public void setTransactionOwner(Account transactionOwner) {
        this.transactionOwner = transactionOwner;
    }

    public Account getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(Account targetAccount) {
        this.targetAccount = targetAccount;
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

    public LocalDate getInitiationDate() {
        return initiationDate;
    }

    public void setInitiationDate(LocalDate initiationDate) {
        this.initiationDate = initiationDate;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return transaction_id.equals(that.transaction_id) && transactionOwner.equals(that.transactionOwner) && targetAccount.equals(that.targetAccount) && targetOwnerName.equals(that.targetOwnerName) && amount.equals(that.amount) && initiationDate.equals(that.initiationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(transaction_id, transactionOwner, targetAccount, targetOwnerName, amount, initiationDate);
    }


}
