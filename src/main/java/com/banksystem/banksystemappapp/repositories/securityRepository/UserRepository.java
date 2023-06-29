package com.banksystem.banksystemappapp.repositories.securityRepository;

import com.banksystem.banksystemappapp.models.users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String name);

    Optional<User> findByUserName(String userName);
}
