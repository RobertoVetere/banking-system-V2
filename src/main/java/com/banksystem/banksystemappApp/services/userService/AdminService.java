package com.banksystem.banksystemappApp.services.userService;

import com.banksystem.banksystemappApp.models.users.Admin;
import com.banksystem.banksystemappApp.repositories.userRepositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    AdminRepository adminRepository;




}
