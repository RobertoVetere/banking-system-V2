package com.banksystem.banksystemappapp.services.bankService;

import com.banksystem.banksystemappapp.models.bank.Bank;
import com.banksystem.banksystemappapp.models.users.Admin;
import com.banksystem.banksystemappapp.models.users.Role;
import com.banksystem.banksystemappapp.repositories.bankRepository.BankRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

/**
 * La clase BankService proporciona métodos para gestionar bancos y administradores.
 */
@Service
public class BankService {

    @Autowired
    BankRepository bankRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    RoleRepository roleRepository;

    /**
     * Crea un nuevo banco con la información proporcionada.
     *
     * @param bank El objeto Bank que contiene los detalles del banco.
     * @return El objeto Bank creado.
     */
    public Bank createBank(Bank bank) {
        Bank newBank = new Bank(bank.getName(), bank.getPassword());

        String encodedPassword = passwordEncoder.encode(newBank.getPassword());
        newBank.setPassword(encodedPassword);

        return bankRepository.save(newBank);
    }

    /**
     * Crea un nuevo administrador con la información proporcionada.
     *
     * @param admin El objeto Admin que contiene los detalles del administrador.
     * @return El objeto Admin creado.
     * @throws ResponseStatusException Si el nombre de usuario ya está en uso.
     */
    public Admin createAdmin(Admin admin) {
        Optional<Admin> adminUserNameRepeat = adminRepository.findByUserName(admin.getUserName());

        if (adminUserNameRepeat.isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Lo siento, el nombre de usuario ya está en uso");
        }

        Admin newAdmin = new Admin(admin.getName(), admin.getUserName(), admin.getPassword());

        String encodedPassword = passwordEncoder.encode(newAdmin.getPassword());
        newAdmin.setPassword(encodedPassword);
        newAdmin = adminRepository.save(newAdmin);

        Role role = roleRepository.save(new Role("ADMIN", newAdmin));
        Role role2 = roleRepository.save(new Role("USER", newAdmin));

        return adminRepository.save(newAdmin);
    }
}