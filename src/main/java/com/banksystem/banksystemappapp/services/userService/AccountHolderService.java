package com.banksystem.banksystemappapp.services.userService;

import com.banksystem.banksystemappapp.dtos.AccountHolderDTO;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.Role;
import com.banksystem.banksystemappapp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class AccountHolderService {

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    /**
     * Obtiene una lista de todos los titulares de cuenta.
     *
     * @return Una lista de objetos AccountHolder.
     */
    public List<AccountHolder> findAllAccountHolders() {
        return accountHolderRepository.findAll();
    }

    /**
     * Agrega un titular de cuenta.
     *
     * @param userDetails    Los detalles del usuario autenticado.
     * @param accountHolderDTO Los detalles del titular de cuenta.
     * @return El objeto AccountHolder creado.
     * @throws ResponseStatusException Si el nombre de usuario ya está en uso.
     */
    public AccountHolder addAccountHolder(UserDetails userDetails, AccountHolderDTO accountHolderDTO) {
        try {
            userRepository.findByUserName(userDetails.getUsername()).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No está autorizado para realizar esta acción");
        }

        Optional<AccountHolder> accountHolderValidate = accountHolderRepository.findByUserName(accountHolderDTO.getUserName());

        if (accountHolderValidate.isPresent()) {
            if (accountHolderValidate.get().getUserName().equals(accountHolderDTO.getUserName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Lo siento, el nombre de usuario ya está en uso");
            }
        }

        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(), accountHolderDTO.getUserName(),
                accountHolderDTO.getPassword(), accountHolderDTO.getDateOfBirth(), accountHolderDTO.getMailingAddress(),
                accountHolderDTO.getPrimaryAddress());

        String encodedPassword = passwordEncoder.encode(accountHolder.getPassword());
        accountHolder.setPassword(encodedPassword);
        accountHolder = accountHolderRepository.save(accountHolder);
        Role role = roleRepository.save(new Role("USER", accountHolder));

        return accountHolderRepository.save(accountHolder);
    }
}