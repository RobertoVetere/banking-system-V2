package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountStatus;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.bank.Bank;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Admin;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.DynamicUpdate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.banksystem.banksystemappApp.models.RandomClass.randomAccountNumber;

@Entity
@DynamicUpdate
@Table(name = "accounts")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    final String accountNumber = randomAccountNumber();

    private BigDecimal balance;

    private String secretKey;
    final BigDecimal penaltyFee = new BigDecimal("40.00");
    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    @ManyToOne
    @JoinColumn(name = "bank")
    private Bank bank;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private LocalDate createdDate = LocalDate.now();

    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus = AccountStatus.ACTIVE;

    @Enumerated(EnumType.STRING)
    private AccountType accountType;

    @OneToMany(mappedBy = "transactionOwner")
    @JsonIgnore
    private List<Transaction> transactionList = new ArrayList<>();

    @OneToMany(mappedBy = "targetAccount")
    @JsonIgnore
    private List<Transaction> targetAccount = new ArrayList<>();

    public Account() {
    }

    public Account(BigDecimal balance, String secretKey, AccountHolder primaryOwner,
                   AccountHolder secondaryOwner,  AccountType accountType) {
        setBalance(balance);
        this.secretKey = secretKey;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
        setAccountType(accountType);
    }


    public Bank getBank() {return bank;}

    public void setBank(Bank bank) {this.bank = bank;}

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public List<Transaction> getTransactionList() {
        return transactionList;
    }

    public void setTransactionList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

    public List<Transaction> getTargetAccount() {
        return targetAccount;
    }

    public void setTargetAccount(List<Transaction> targetAccount) {
        this.targetAccount = targetAccount;
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

        try{
            BigDecimal zero = new BigDecimal("0.00");

            if (balance.compareTo(zero) > 0) {

                this.balance = balance;
            }else{

            throw new  IllegalArgumentException("balance doesn't must be zero");

            }


        }catch (IllegalArgumentException exception){

            throw new  IllegalArgumentException("balance doesn't must be zero");
        }
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
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
        return id.equals(account.id) && accountNumber.equals(account.accountNumber) && balance.equals(account.balance) && secretKey.equals(account.secretKey) && primaryOwner.equals(account.primaryOwner) && secondaryOwner.equals(account.secondaryOwner) && penaltyFee.equals(account.penaltyFee) && createdDate.equals(account.createdDate) && accountStatus == account.accountStatus && transactionList.equals(account.transactionList) && targetAccount.equals(account.targetAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, accountNumber, balance, secretKey, primaryOwner, secondaryOwner, penaltyFee, createdDate, accountStatus, transactionList, targetAccount);
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
