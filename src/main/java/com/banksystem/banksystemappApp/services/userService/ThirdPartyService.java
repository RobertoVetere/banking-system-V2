package com.banksystem.banksystemappApp.services.userService;
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

            Optional<ThirdParty> thirdPartyOptional = thirdPartyRepository.findByName(thirdParty.getName());

                if (thirdPartyOptional.isPresent()){

                    if (thirdPartyOptional.get().getUserName().equals(thirdParty.getUserName())){

                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Sorry username already taken");
                    }
        }

                ThirdParty newThirdParty = new ThirdParty(thirdParty.getName(),thirdParty.getUserName(),thirdParty.getPassword(),thirdParty.getHashedKey());

        String encodedPassword = passwordEncoder.encode(newThirdParty.getHashedKey());
        newThirdParty.setHashedKey(encodedPassword);

        String encodedPassword2 = passwordEncoder.encode(newThirdParty.getPassword());
        newThirdParty.setPassword(encodedPassword2);
        newThirdParty = thirdPartyRepository.save(newThirdParty);
        Role role = roleRepository.save(new Role("THIRDPARTY", newThirdParty));

        return thirdPartyRepository.save(newThirdParty);
    }

}


