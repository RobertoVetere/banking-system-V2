package com.banksystem.banksystemappApp.models.transaction;

import com.banksystem.banksystemappApp.models.accounts.Account;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public abstract class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long transaction_id;

    @ManyToMany
    @JoinTable(
            name = "transaction",
            joinColumns = @JoinColumn(name = "transaction_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id"))
    private List<Account> accountList = new ArrayList<>();
/*
    public void deposit(Account account){
        account.getBalance();
    }

 */
}
