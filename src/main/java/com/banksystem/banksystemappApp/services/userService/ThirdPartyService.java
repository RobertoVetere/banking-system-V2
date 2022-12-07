package com.banksystem.banksystemappApp.services.userService;

import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.repositories.userRepositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ThirdPartyService {

    @Autowired
    ThirdPartyRepository thirdPartyRepository;

    public List<ThirdParty> findAllThirdParty() {
        return thirdPartyRepository.findAll();
    }
}
