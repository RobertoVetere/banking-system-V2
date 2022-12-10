package com.banksystem.banksystemappApp.controllers.DTO;

import com.banksystem.banksystemappApp.models.users.Address;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import org.springframework.lang.NonNull;

import java.time.LocalDate;

public class AccountHolderDTO {

    private String name;
    @NonNull
    private String userName;

    private String password;

    private LocalDate dateOfBirth;

    @Embedded
    private Address mailingAddress;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "address", column = @Column(name = "physical_address")),
            @AttributeOverride(name = "postalCode", column = @Column(name = "physical_postal_code")),
            @AttributeOverride(name = "city", column = @Column(name = "physical_city")),
            @AttributeOverride(name = "country", column = @Column(name = "physical_country"))
    })

    private Address primaryAddress;

    public AccountHolderDTO(String name, String userName, String password, LocalDate dateOfBirth, Address mailingAddress, Address primaryAddress) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.mailingAddress = mailingAddress;
        this.primaryAddress = primaryAddress;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getMailingAddress() {
        return mailingAddress;
    }

    public void setMailingAddress(Address mailingAddress) {
        this.mailingAddress = mailingAddress;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }
}
