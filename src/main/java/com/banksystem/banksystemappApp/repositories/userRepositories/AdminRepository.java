package com.banksystem.banksystemappApp.repositories.userRepositories;

import com.banksystem.banksystemappApp.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
        Optional<Admin>  findByUserName (String userName);
}
