package com.banksystem.banksystemappApp.models.users;

import com.banksystem.banksystemappApp.models.accounts.Account;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Admin extends User{

    //@OneToMany(mappedBy = "admin")
    //@JsonIgnore
    //private List<Account> accountList = new ArrayList<>();

    public Admin() {
    }

    public Admin(String name, String userName, String password) {
        super(name, userName, password);
    }


}
