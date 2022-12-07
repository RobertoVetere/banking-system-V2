package com.banksystem.banksystemappApp.models.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
public class ThirdParty extends User {

    @Column(name = "Password")
    private String hashedKey;

    public ThirdParty() {
    }

    public ThirdParty(String name, String password, String hashedKey) {
        super(name, password);
        this.hashedKey = hashedKey;
    }

    public String getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(String hashedKey) {
        this.hashedKey = hashedKey;
    }

    @Override
    public String toString() {
        return super.toString() + "ThirdParty{" +
                "hashedKey='" + hashedKey + '\'' +
                '}';
    }
}
