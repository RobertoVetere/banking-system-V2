package com.banksystem.banksystemappApp.services.userService;

import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Role;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public List<ThirdParty> findAllThirdParty() {
        return thirdPartyRepository.findAll();
    }

    public ThirdParty addThirdParty(UserDetails userDetails , ThirdParty thirdParty) {

        userRepository.findByUserName(userDetails.getUsername()).get();

            Optional<ThirdParty> accountHolderValidate = thirdPartyRepository.findByName(thirdParty.getName());

                if (accountHolderValidate.isPresent()){

                    if (accountHolderValidate.get().getName().equals(thirdParty.getName())){

                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Sorry username already taken");
                    }
        }

        String encodedPassword = passwordEncoder.encode(thirdParty.getHashedKey());
        thirdParty.setHashedKey(encodedPassword);
        thirdParty = thirdPartyRepository.save(thirdParty);
        //Role role = roleRepository.save(new Role("THIRDPARTY", thirdParty));

        return thirdPartyRepository.save(thirdParty);
    }
}
