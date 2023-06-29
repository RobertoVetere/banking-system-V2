package com.banksystem.banksystemappapp.services.accountService;

import com.banksystem.banksystemappapp.dtos.AccountDTO;
import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.accounts.Savings;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.User;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import com.banksystem.banksystemappapp.utilMethod.UtilMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

/**
 * La clase SavingsService proporciona métodos para gestionar cuentas de ahorro.
 */
@Service
public class SavingsService {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UtilMethod utilMethod;

    /**
     * Agrega una nueva cuenta de ahorro basada en el AccountDTO proporcionado.
     *
     * @param userDetails Los detalles del usuario que contienen el nombre de usuario.
     * @param accountDTO  El AccountDTO que contiene los detalles de la cuenta.
     * @return El objeto Account creado.
     * @throws ResponseStatusException Si el titular principal o secundario no se encuentra.
     */
    public Account addSaving(UserDetails userDetails, AccountDTO accountDTO) {
        User user = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular principal no encontrado"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular secundario no encontrado"));
        }

        Account saving = new Savings(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, AccountType.SAVINGS);

        String encodedPassword = passwordEncoder.encode(saving.getSecretKey());
        saving.setSecretKey(encodedPassword);
        saving = accountRepository.save(saving);

        return accountRepository.save(saving);
    }

    /**
     * Recupera el saldo de una cuenta de ahorro con el ID especificado, basándose en los detalles del usuario.
     *
     * @param userDetails Los detalles del usuario que contienen el nombre de usuario.
     * @param id           El ID de la cuenta de ahorro.
     * @param secretKey    La clave secreta de la cuenta de ahorro.
     * @return El saldo de la cuenta de ahorro.
     * @throws ResponseStatusException Si la cuenta de ahorro no se encuentra o las credenciales son incorrectas.
     */
    public BigDecimal showSavingBalance(UserDetails userDetails, Long id, String secretKey) {
        User user = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no encontrado"));

        Savings account = savingsRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta de ahorro no encontrada"));

        if (user.getUserName().equals(account.getPrimaryOwner().getUserName())) {
            try {
                if (account.getSecretKey().equals(secretKey)) {
                    LocalDate now = LocalDate.now();
                    Period period = Period.between(account.getCreatedDate(), account.getCheckLastConnection());

                    if (period.getDays() > 365 && period.getDays() <= 730) {
                        account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                        savingsRepository.save(account);
                    } else {
                        Period period2 = Period.between(now, account.getCheckLastConnection());

                        if (period.getDays() > 365) {
                            account.setBalance(account.getBalance().multiply(BigDecimal.valueOf(account.getInterestRate())));
                            savingsRepository.save(account);
                        }
                    }

                    account.setCheckLastConnection(now);
                }
            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Lo siento, la contraseña es incorrecta");
            }

            return account.getBalance();
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Lo siento, la contraseña es incorrecta");
    }
}