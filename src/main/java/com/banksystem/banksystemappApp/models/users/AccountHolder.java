package com.banksystem.banksystemappApp.models.users;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "primary_owner")
public class AccountHolder extends User {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonFormat(pattern = "dd-MM-yyyy")
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

    @OneToMany(mappedBy = "primaryOwner")
    @JsonIgnore
    private List<Account> accountList = new ArrayList<>();

    @OneToMany(mappedBy = "secondaryOwner")
    @JsonIgnore
    private List<Account> accountListSecondary = new ArrayList<>();


    public AccountHolder() {
    }


    public AccountHolder(String name, String userName, String password, LocalDate dateOfBirth, Address mailingAddress, Address primaryAddress) {
        super(name, userName, password);
        this.dateOfBirth = dateOfBirth;
        this.mailingAddress = mailingAddress;
        this.primaryAddress = primaryAddress;
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

    public List<Account> getAccountList() {
        return accountList;
    }

    public void setAccountList(List<Account> accountList) {
        this.accountList = accountList;
    }

    public List<Account> getAccountListSecondary() {
        return accountListSecondary;
    }

    public void setAccountListSecondary(List<Account> accountListSecondary) {
        this.accountListSecondary = accountListSecondary;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        AccountHolder that = (AccountHolder) o;
        return dateOfBirth.equals(that.dateOfBirth) && mailingAddress.equals(that.mailingAddress) && primaryAddress.equals(that.primaryAddress) && accountList.equals(that.accountList) && accountListSecondary.equals(that.accountListSecondary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), dateOfBirth, mailingAddress, primaryAddress, accountList, accountListSecondary);
    }

    @Override
    public String toString() {
        return super.toString() + "AccountHolder{" +
                "dateOfBirth=" + dateOfBirth +
                ", mailingAddress=" + mailingAddress +
                ", primaryAddress=" + primaryAddress +
                ", accountList=" + accountList +
                ", accountListSecondary=" + accountListSecondary +
                '}';
    }
}
