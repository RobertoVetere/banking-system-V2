package com.banksystem.banksystemappapp.services.transactionService;

import com.banksystem.banksystemappapp.dtos.ThirdPartyTransactionDTO;
import com.banksystem.banksystemappapp.dtos.TransactionDTO;
import com.banksystem.banksystemappapp.enums.TransactionType;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.transaction.Transaction;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

/**
 * La clase TransactionService proporciona métodos para realizar transacciones financieras.
 */
@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Realiza una transferencia de fondos desde una cuenta de origen a una cuenta de destino.
     *
     * @param userDetails   Los detalles del usuario autenticado.
     * @param transactionDTO Los detalles de la transacción.
     * @return El objeto Transaction creado.
     * @throws ResponseStatusException Si las credenciales son incorrectas o los fondos son insuficientes.
     */
    public Transaction makeTransfer(UserDetails userDetails, TransactionDTO transactionDTO) {
        Account transactionOwner = accountRepository.findByAccountNumber(transactionDTO.getTransactionOwnerAccountNumber());
        Account targetAccount = accountRepository.findByAccountNumber(transactionDTO.getTargetAccountNumber());

        if (userDetails.getUsername().equals(transactionOwner.getPrimaryOwner().getUserName()) ||
                userDetails.getUsername().equals(transactionOwner.getSecondaryOwner().getUserName())) {

            if (transactionDTO.getTransactionOwnerAccountNumber().equals(transactionOwner.getAccountNumber())) {

                if (transactionOwner.getBalance().compareTo(transactionDTO.getAmount()) > 0) {
                    transactionOwner.setBalance(transactionOwner.getBalance().subtract(transactionDTO.getAmount()));
                    targetAccount.setBalance(targetAccount.getBalance().add(transactionDTO.getAmount()));

                    accountRepository.saveAll(List.of(transactionOwner, targetAccount));

                    Transaction transaction = new Transaction(transactionDTO.getTransactionOwnerAccountNumber(),
                            transactionDTO.getTargetAccountNumber(), transactionOwner, targetAccount,
                            transactionDTO.getTargetOwnerName(), transactionDTO.getAmount(), TransactionType.TRANSFER);

                    return transactionRepository.save(transaction);
                } else {
                    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Lo siento, fondos insuficientes");
                }
            }

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales incorrectas");
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Datos incorrectos");
    }

    /**
     * Realiza un depósito en una cuenta.
     *
     * @param userDetails Los detalles del usuario autenticado.
     * @param id          El ID de la cuenta.
     * @param deposit     La cantidad a depositar.
     * @return El objeto Account actualizado.
     * @throws ResponseStatusException Si las credenciales son incorrectas.
     */
    public Account deposit(UserDetails userDetails, Long id, BigDecimal deposit) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (userDetails.getUsername().equals(account.getPrimaryOwner().getUserName())) {
            account.setBalance(account.getBalance().add(deposit));

            Transaction transaction = new Transaction(account.getAccountNumber(), account, deposit, TransactionType.DEPOSIT);
            transactionRepository.save(transaction);

            return accountRepository.save(account);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Datos incorrectos");
    }

    /**
     * Realiza un retiro de una cuenta.
     *
     * @param userDetails Los detalles del usuario autenticado.
     * @param id          El ID de la cuenta.
     * @param withdrawal  La cantidad a retirar.
     * @return El objeto Account actualizado.
     * @throws ResponseStatusException Si las credenciales son incorrectas o los fondos son insuficientes.
     */
    public Account withdrawal(UserDetails userDetails, Long id, BigDecimal withdrawal) {
        Account account = accountRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

        if (userDetails.getUsername().equals(account.getPrimaryOwner().getUserName())) {
            if (account.getBalance().compareTo(withdrawal) < 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lo siento, fondos insuficientes");
            } else {
                account.setBalance(account.getBalance().subtract(withdrawal));

                Transaction transaction = new Transaction(account.getAccountNumber(), account, withdrawal, TransactionType.WITHDRAW);
                transactionRepository.save(transaction);
            }
            return accountRepository.save(account);
        }

        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Datos incorrectos");
    }

    /**
     * Realiza un pago de tercero desde una cuenta.
     *
     * @param thirdPartyTransactionDTO Los detalles de la transacción de terceros.
     * @param secretKey                La clave secreta proporcionada.
     * @param hashedKey                La clave hasheada proporcionada.
     * @return El objeto Account actualizado.
     * @throws ResponseStatusException Si los fondos son insuficientes.
     */
    public Account thirdPartyPayment(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String secretKey, String hashedKey) {
        Account account = accountRepository.findByAccountNumber(thirdPartyTransactionDTO.getAccountNumber());

        // if (account.getSecretKey().equals(secretKey)){

        if (account.getBalance().compareTo(thirdPartyTransactionDTO.getAmount()) > 0) {
            account.setBalance(account.getBalance().subtract(thirdPartyTransactionDTO.getAmount()));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Lo siento, fondos insuficientes");
        }
        /*
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "¡UPS! Contraseña incorrecta");
        }
         */

        Transaction transaction = new Transaction(account.getAccountNumber(), account, thirdPartyTransactionDTO.getAmount(), TransactionType.THIRD_PARTY_PAYMENT);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }

    /**
     * Realiza un recibo de tercero en una cuenta.
     *
     * @param thirdPartyTransactionDTO Los detalles de la transacción de terceros.
     * @param secretKey                La clave secreta proporcionada.
     * @param hashedKey                La clave hasheada proporcionada.
     * @return El objeto Account actualizado.
     */
    public Account thirdPartyReceipt(ThirdPartyTransactionDTO thirdPartyTransactionDTO, String secretKey, String hashedKey) {
        Account account = accountRepository.findByAccountNumber(thirdPartyTransactionDTO.getAccountNumber());
        account.setBalance(account.getBalance().add(thirdPartyTransactionDTO.getAmount()));

        Transaction transaction = new Transaction(account.getAccountNumber(), account, thirdPartyTransactionDTO.getAmount(), TransactionType.THIRD_PARTY_RECEIPTS);
        transactionRepository.save(transaction);

        return accountRepository.save(account);
    }
}