package com.banksystem.banksystemappApp.models.bank;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "bank")
    @JsonIgnore
    private List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "bank")
    @JsonIgnore
    private List<Account> accountList = new ArrayList<>();

    public Bank() {
    }

    public Bank(List<User> userList, List<Account> accountList) {
        this.userList = userList;
        this.accountList = accountList;
    }

    public List<User> getUserListr() {
        return userList;
    }

    public void setUserListr(List<User> userListr) {
        this.userList = userListr;
    }

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }
}
