package com.banksystem.banksystemappApp.services.userService;

import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.controllers.DTO.AccountHolderDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Account;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Role;
import com.banksystem.banksystemappApp.models.users.User;
import com.banksystem.banksystemappApp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.swing.text.html.Option;
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

    public List<AccountHolder> findAllAccountHolders() {
        return accountHolderRepository.findAll();
    }



    public AccountHolder addAccountHolder(UserDetails userDetails , AccountHolderDTO accountHolderDTO) {

        userRepository.findByUserName(userDetails.getUsername()).get();

        Optional<AccountHolder> accountHolderValidate = accountHolderRepository.findByUserName(accountHolderDTO.getUserName());

        if (accountHolderValidate.isPresent()){

            if (accountHolderValidate.get().getUserName().equals(accountHolderDTO.getUserName())){

                throw new ResponseStatusException(HttpStatus.CONFLICT, "Sorry username already taken");
            }

        }

        AccountHolder accountHolder = new AccountHolder(accountHolderDTO.getName(),accountHolderDTO.getUserName(),accountHolderDTO.getPassword(),accountHolderDTO.getDateOfBirth(),
                accountHolderDTO.getMailingAddress(),accountHolderDTO.getPrimaryAddress());


        String encodedPassword = passwordEncoder.encode(accountHolder.getPassword());
        accountHolder.setPassword(encodedPassword);
        accountHolder = accountHolderRepository.save(accountHolder);
        Role role = roleRepository.save(new Role("ACCOUNTHOLDER", accountHolder));

        return accountHolderRepository.save(accountHolder);
    }

}
