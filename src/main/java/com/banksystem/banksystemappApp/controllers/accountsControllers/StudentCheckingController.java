package com.banksystem.banksystemappApp.controllers.accountsControllers;

import com.banksystem.banksystemappApp.services.accountService.StudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class StudentCheckingController {

    @Autowired
    StudentCheckingService studentCheckingService;
}
