package com.banksystem.banksystemappApp.repositories.securityRepository;

import com.banksystem.banksystemappApp.models.users.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
}
