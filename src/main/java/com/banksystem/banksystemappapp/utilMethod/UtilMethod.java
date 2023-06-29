package com.banksystem.banksystemappapp.utilMethod;

import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.accounts.Savings;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

public class UtilMethod {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountRepository accountRepository;

    public UtilMethod() {
    }

    /**
     * Calcula la tasa de interés de una cuenta y actualiza el saldo.
     *
     * @param account La cuenta.
     * @return La cuenta actualizada con la tasa de interés aplicada.
     */
    public Account calculateInterestRate(Account account) {
        try {
            Savings saving = new Savings(account.getBalance(), account.getSecretKey(), account.getPrimaryOwner(),
                    account.getSecondaryOwner(), account.getAccountType());

            LocalDate now = LocalDate.now();
            Period period = Period.between(account.getCreatedDate(), saving.getCheckLastConnection());

            if (period.getDays() > 365 & period.getDays() <= 730) {
                saving.setBalance(account.getBalance().multiply(BigDecimal.valueOf(saving.getInterestRate())));
                savingsRepository.save(saving);
            } else {
                Period period2 = Period.between(now, saving.getCheckLastConnection());

                if (period.getDays() > 365) {
                    saving.setBalance(saving.getBalance().multiply(BigDecimal.valueOf(saving.getInterestRate())));
                    savingsRepository.save(saving);
                }
            }
            saving.setCheckLastConnection(now);
            return saving;
        } catch (Exception e) {
            // Manejar cualquier excepción y realizar alguna acción o lanzar una excepción personalizada si es necesario
            throw new RuntimeException("Error al calcular la tasa de interés de la cuenta");
        }
    }
}