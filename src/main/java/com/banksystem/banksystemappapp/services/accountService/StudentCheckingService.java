package com.banksystem.banksystemappapp.services.accountService;

import com.banksystem.banksystemappapp.repositories.accountRepositories.StudentCheckingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StudentCheckingService {

    @Autowired
    StudentCheckingRepository studentCheckingRepository;
}
