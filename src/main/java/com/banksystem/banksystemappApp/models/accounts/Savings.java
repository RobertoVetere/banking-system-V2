package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Savings extends Account {

    private BigDecimal minimumBalance = new BigDecimal("1000.00");
    private Double interestRate = 0.0025;

    private LocalDate checkLastConnection;

    public Savings() {
    }

    public Savings(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, accountType);
    }

    public Savings(BigDecimal balance, String secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   BigDecimal minimumBalance, Double interestRate, AccountType accountType) {
        super(balance, secretKey, primaryOwner, secondaryOwner, accountType);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public LocalDate getCheckLastConnection() {
        return checkLastConnection;
    }

    public void setCheckLastConnection(LocalDate checkLastConnection) {
        this.checkLastConnection = checkLastConnection;
    }

    @Override
    public AccountType getAccountType() {
        return super.getAccountType();
    }

    @Override
    public void setAccountType(AccountType accountType) {
        super.setAccountType(AccountType.SAVINGS);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {

        BigDecimal limit = new BigDecimal("100.00");

        if (minimumBalance.compareTo(limit) < 0) {

            this.minimumBalance = limit;

        }else{

            this.minimumBalance = minimumBalance;

        }
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {

        if (interestRate > 0.5) {

            this.interestRate = 0.5;

        }else{
            this.interestRate = interestRate;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Savings savings = (Savings) o;
        return minimumBalance.equals(savings.minimumBalance) && interestRate.equals(savings.interestRate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), minimumBalance, interestRate);
    }

    @Override
    public String toString() {
        return super.toString() + "Savings{" +
                "minimumBalance=" + minimumBalance +
                ", interestRate=" + interestRate +
                '}';
    }
}
