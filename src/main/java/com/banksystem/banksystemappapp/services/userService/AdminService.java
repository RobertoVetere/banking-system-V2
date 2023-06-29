package com.banksystem.banksystemappapp.services.userService;
import com.banksystem.banksystemappapp.repositories.userRepositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;

    
}
