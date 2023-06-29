package com.banksystem.banksystemappapp.services.accountService;

import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

/**
 * La clase AccountService proporciona métodos para gestionar las cuentas bancarias.
 */
@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Actualiza el saldo de una cuenta con el ID especificado.
     *
     * @param id      El ID de la cuenta.
     * @param balance El nuevo saldo a establecer.
     * @return El objeto Account actualizado.
     * @throws ResponseStatusException Si no se encuentra la cuenta.
     */
    public Account updateAccountBalance(Long id, BigDecimal balance) {
        try {
            Account account = accountRepository.findById(id).orElseThrow(
                    () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
            account.setBalance(balance);
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al actualizar el saldo de la cuenta");
        }
    }

    /**
     * Obtiene el saldo de una cuenta con el ID especificado, en función de los detalles del usuario.
     *
     * @param userDetails Los detalles del usuario que contienen el nombre de usuario.
     * @param id          El ID de la cuenta.
     * @return El saldo de la cuenta.
     * @throws ResponseStatusException Si no se encuentra la cuenta o las credenciales son incorrectas.
     */
    public BigDecimal showAccountBalance(UserDetails userDetails, Long id) {
        try {
            userRepository.findByUserName(userDetails.getUsername()).get();

            if (userDetails.getUsername().equals(accountRepository.findById(id).get().getPrimaryOwner().getUserName())) {
                Account account = accountRepository.findById(id).orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));
                return account.getBalance();
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al obtener el saldo de la cuenta");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
    }
}