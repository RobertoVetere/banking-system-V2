package com.banksystem.banksystemappApp.controllers.usersControllers;

import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.services.userService.ThirdPartyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ThirdPartyController {

    @Autowired
    ThirdPartyService thirdPartyService;

    @GetMapping("/third-party-All")
    @ResponseStatus(HttpStatus.OK)
    public List<ThirdParty> findAllAccountHolders(){
        return thirdPartyService.findAllThirdParty();
    }
}
