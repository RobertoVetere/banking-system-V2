package com.banksystem.banksystemappApp.repositories.userRepositories;

import com.banksystem.banksystemappApp.models.users.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
}
