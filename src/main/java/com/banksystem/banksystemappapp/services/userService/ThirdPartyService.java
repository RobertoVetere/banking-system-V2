package com.banksystem.banksystemappapp.services.userService;

import com.banksystem.banksystemappapp.models.users.Role;
import com.banksystem.banksystemappapp.models.users.ThirdParty;
import com.banksystem.banksystemappapp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.ThirdPartyRepository;
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

    /**
     * Obtiene una lista de todos los terceros.
     *
     * @return Una lista de objetos ThirdParty.
     */
    public List<ThirdParty> findAllThirdParty() {
        return thirdPartyRepository.findAll();
    }

    /**
     * Agrega un tercero.
     *
     * @param userDetails Los detalles del usuario autenticado.
     * @param thirdParty  Los detalles del tercero.
     * @return El objeto ThirdParty creado.
     * @throws ResponseStatusException Si el nombre de usuario ya está en uso.
     */
    public ThirdParty addThirdParty(UserDetails userDetails, ThirdParty thirdParty) {
        try {
            userRepository.findByUserName(userDetails.getUsername()).get();
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "No está autorizado para realizar esta acción");
        }

        Optional<ThirdParty> thirdPartyOptional = thirdPartyRepository.findByName(thirdParty.getName());

        if (thirdPartyOptional.isPresent()) {
            if (thirdPartyOptional.get().getUserName().equals(thirdParty.getUserName())) {
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Lo siento, el nombre de usuario ya está en uso");
            }
        }

        ThirdParty newThirdParty = new ThirdParty(thirdParty.getName(), thirdParty.getUserName(),
                thirdParty.getPassword(), thirdParty.getHashedKey());

        String encodedKey = passwordEncoder.encode(newThirdParty.getHashedKey());
        newThirdParty.setHashedKey(encodedKey);

        String encodedPassword = passwordEncoder.encode(newThirdParty.getPassword());
        newThirdParty.setPassword(encodedPassword);

        newThirdParty = thirdPartyRepository.save(newThirdParty);
        Role role = roleRepository.save(new Role("THIRDPARTY", newThirdParty));

        return thirdPartyRepository.save(newThirdParty);
    }
}