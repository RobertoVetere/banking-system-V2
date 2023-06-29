package com.banksystem.banksystemappapp.services.accountService;

import com.banksystem.banksystemappapp.dtos.AccountDTO;
import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.accounts.Account;
import com.banksystem.banksystemappapp.models.accounts.Checking;
import com.banksystem.banksystemappapp.models.accounts.StudentChecking;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.repositories.accountRepositories.AccountRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappapp.repositories.accountRepositories.StudentCheckingRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

/**
 * La clase CheckingService proporciona métodos para gestionar las cuentas corrientes.
 */
@Service
public class CheckingService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    StudentCheckingRepository studentCheckingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    /**
     * Recupera todas las cuentas corrientes.
     *
     * @return Una lista de todas las cuentas corrientes.
     */
    public List<Checking> findAllCheckings() {
        try {
            return checkingRepository.findAll();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al recuperar las cuentas corrientes");
        }
    }

    /**
     * Agrega una nueva cuenta corriente basada en el AccountDTO proporcionado.
     *
     * @param accountDTO El AccountDTO que contiene los detalles de la cuenta.
     * @return El objeto Account creado.
     * @throws ResponseStatusException Si no se encuentra el titular principal o secundario.
     */
    public Account addChecking(AccountDTO accountDTO) {
        try {
            AccountHolder primaryOwner = accountHolderRepository.findById(accountDTO.getPrimaryOwnerId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular principal no encontrado"));

            AccountHolder secondaryOwner = null;
            if (accountDTO.getSecondaryOwnerId() != null) {
                secondaryOwner = accountHolderRepository.findById(accountDTO.getSecondaryOwnerId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Titular secundario no encontrado"));
            }

            if (Period.between(primaryOwner.getDateOfBirth(), LocalDate.now()).getYears() < 24) {
                Account studentChecking = new StudentChecking(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, AccountType.STUDENTCHECKING);

                String encodedPassword = passwordEncoder.encode(studentChecking.getSecretKey());
                studentChecking.setSecretKey(encodedPassword);
                studentChecking = accountRepository.save(studentChecking);

                return accountRepository.save(studentChecking);
            } else {
                Account checking = new Checking(accountDTO.getBalance(), accountDTO.getSecretKey(), primaryOwner, secondaryOwner, AccountType.CHECKING);

                String encodedPassword = passwordEncoder.encode(checking.getSecretKey());
                checking.setSecretKey(encodedPassword);
                checking = accountRepository.save(checking);

                return accountRepository.save(checking);
            }
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al agregar la cuenta corriente");
        }
    }

    /**
     * Elimina una cuenta corriente con el ID especificado.
     *
     * @param id El ID de la cuenta corriente a eliminar.
     * @throws ResponseStatusException Si no se encuentra la cuenta.
     */
    public void deleteChecking(Long id) {
        try {
            Account account = accountRepository.findById(id).orElseThrow(() ->
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Cuenta no encontrada"));

            accountRepository.deleteById(account.getId());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error al eliminar la cuenta corriente");
        }
    }
}