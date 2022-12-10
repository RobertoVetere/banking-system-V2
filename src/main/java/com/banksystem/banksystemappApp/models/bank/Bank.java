package com.banksystem.banksystemappApp.models.bank;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.users.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "bank")
public class Bank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String name;

    private String password;

    @OneToMany(mappedBy = "bank")
    @JsonIgnore
    private List<User> userList = new ArrayList<>();

    @OneToMany(mappedBy = "bank")
    @JsonIgnore
    private List<Account> accountList = new ArrayList<>();

    public Bank() {
    }

    public Bank(String name, String password) {
        setName(name);
        setPassword(password);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
