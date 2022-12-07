package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountStatus;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@DynamicUpdate
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal balance;

    private Long secretKey;
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    private BigDecimal penaltyFee;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @ManyToMany(mappedBy = "accountList")
    private List<Transaction> transactionList = new ArrayList<>();

    public Account() {
    }

    public Account(BigDecimal balance, Long secretKey, AccountHolder primaryOwner,
                   AccountHolder secondaryOwner, BigDecimal penaltyFee) {
        this.balance = balance;
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        this.penaltyFee = penaltyFee;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Long getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(Long secretKey) {
        this.secretKey = secretKey;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }

    public void setPenaltyFee(BigDecimal penaltyFee) {
        this.penaltyFee = penaltyFee;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public AccountStatus getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(AccountStatus accountStatus) {
        this.accountStatus = accountStatus;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return id.equals(account.id) && balance.equals(account.balance) && secretKey.equals(account.secretKey) && primaryOwner.equals(account.primaryOwner) && secondaryOwner.equals(account.secondaryOwner) && penaltyFee.equals(account.penaltyFee) && createdDate.equals(account.createdDate) && accountStatus == account.accountStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, createdDate, accountStatus);
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", balance=" + balance +
                ", secretKey=" + secretKey +
                ", primaryOwner=" + primaryOwner +
                ", secondaryOwner=" + secondaryOwner +
                ", penaltyFee=" + penaltyFee +
                ", createdDate=" + createdDate +
                ", accountStatus=" + accountStatus +
                '}';
    }
}
