package com.banksystem.banksystemappapp.services.accountService;

import com.banksystem.banksystemappapp.dtos.AccountDTO;
import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.accounts.CreditCard;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.User;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * La clase CreditCardService proporciona métodos para gestionar tarjetas de crédito.
 */
@Service
public class CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    /**
     * Recupera todas las tarjetas de crédito.
     *
     * @return Una lista de todas las tarjetas de crédito.
     */
    public List<CreditCard> findAllCreditCard() {
        return creditCardRepository.findAll();
    }

    /**
     * Guarda una tarjeta de crédito.
     *
     * @param creditCard La tarjeta de crédito a guardar.
     * @return La tarjeta de crédito guardada.
     */
    public CreditCard saveCreditCard(CreditCard creditCard) {
        return creditCardRepository.save(creditCard);
    }

    /**
     * Agrega una nueva tarjeta de crédito basada en el AccountDTO proporcionado.
     *
     * @param accountDTO El AccountDTO que contiene los detalles de la cuenta.
     * @return El objeto Account creado.
     * @throws ResponseStatusException Si el titular principal o secundario no se encuentra.
     */
    public Account addCreditCard(AccountDTO accountDTO) {
        AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular principal no encontrado"));

        AccountHolder secondaryOwner = null;
        if (accountDTO.getSecondaryOwnerId() != null) {
            secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular secundario no encontrado"));
        }

        Account creditCard = new CreditCard(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, null,
                accountDTO.getCreditLimit(), accountDTO.getInterestRate(), AccountType.CREDITCARD);

        String encodedPassword = passwordEncoder.encode(creditCard.getSecretKey());
        creditCard.setSecretKey(encodedPassword);
        creditCard = accountRepository.save(creditCard);

        return accountRepository.save(creditCard);
    }

    /**
     * Recupera el saldo de una tarjeta de crédito con el ID especificado, basándose en los detalles del usuario.
     *
     * @param userDetails Los detalles del usuario que contienen el nombre de usuario.
     * @param id           El ID de la tarjeta de crédito.
     * @param secretKey    La clave secreta de la tarjeta de crédito.
     * @return El saldo de la tarjeta de crédito.
     * @throws ResponseStatusException Si la tarjeta de crédito no se encuentra o las credenciales son incorrectas.
     */
    public BigDecimal showCreditCardBalance(UserDetails userDetails, Long id, String secretKey) {
        CreditCard account = creditCardRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarjeta de crédito no encontrada"));

        User holder = userRepository.findByUserName(userDetails.getUsername()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (account.getSecretKey().equals(secretKey) && holder.getUserName().equals(accountRepository.findById(id).get().getPrimaryOwner().getUserName())) {
            account.setCheckLastConnection(LocalDate.now());
            LocalDate last = account.getCreatedDate();
            Period period = Period.between(last, account.getCheckLastConnection());
            int days = period.getDays();

            if (days > 31) {
                BigDecimal interest = account.getInterestRate().divide(new BigDecimal(100));
                account.setBalance(account.getBalance().multiply(interest));
                creditCardRepository.save(account);
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Lo siento, credenciales incorrectas");
        }

        return account.getBalance();
    }
}