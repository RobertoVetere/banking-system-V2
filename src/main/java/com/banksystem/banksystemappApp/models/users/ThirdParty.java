package com.banksystem.banksystemappApp.models.users;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ThirdParty extends User {


    public ThirdParty() {
    }


    public ThirdParty(String name, String userName, String password) {
        super(name, userName, password);
    }


    @Override
    public String getPassword() {
        return super.getPassword();
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
