package com.banksystem.banksystemappapp.models.users;

import jakarta.persistence.Entity;

@Entity
public class Admin extends User {

    public Admin() {
    }

    public Admin(String name, String userName, String password) {
        super(name, userName, password);
    }


}
