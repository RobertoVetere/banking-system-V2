package com.banksystem.banksystemappapp.models.users;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
public class ThirdParty extends User {

    private String hashedKey;

    public ThirdParty() {
    }


    public ThirdParty(String name, String userName, String password, String hashedKey) {
        super(name, userName, password);
        this.hashedKey = hashedKey;
    }


    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    @Override
    public void setName(String name) {
        super.setName(name);
    }

    @Override
    public String getUserName() {
        return super.getUserName();
    }

    @Override
    public void setUserName(String userName) {
        super.setUserName(userName);
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
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ThirdParty that = (ThirdParty) o;
        return hashedKey.equals(that.hashedKey);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), hashedKey);
    }
}
