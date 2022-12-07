package com.banksystem.banksystemappApp.models.accounts;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import jakarta.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
public class Savings extends Account {

    private BigDecimal minimumBalance = new BigDecimal("1000.00");
    private Double interestRate = 0.0025;

    public Savings() {
    }

    public Savings(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   BigDecimal penaltyFee) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
    }

    public Savings(BigDecimal balance, Long secretKey, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   BigDecimal penaltyFee, BigDecimal minimumBalance, Double interestRate) {
        super(balance, secretKey, primaryOwner, secondaryOwner, penaltyFee);
        setMinimumBalance(minimumBalance);
        setInterestRate(interestRate);
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {

        BigDecimal limit = new BigDecimal("100.00");

        if (minimumBalance.compareTo(limit) < 0) {

            System.out.println("the minimum balance is 100.00 euro");

            this.minimumBalance = new BigDecimal("100.00");

        }else{

            this.minimumBalance = minimumBalance;

        }
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {

        if (interestRate > 0.5) {

            System.out.println("the interest rate may not be lower than 0.5");

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
