package com.banksystem.banksystemappApp.controllers.usersControllers;

import com.banksystem.banksystemappApp.models.users.Admin;

import com.banksystem.banksystemappApp.services.userService.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class AdminController {

    @Autowired
    AdminService  adminService;

    @GetMapping("/admin-All")
    @ResponseStatus(HttpStatus.OK)
    public List<Admin> findAllAdmins(){
        return adminService.findAllAdmin();
    }

}
