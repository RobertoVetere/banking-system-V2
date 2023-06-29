package com.banksystem.banksystemappapp.repositories.userRepositories;

import com.banksystem.banksystemappapp.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {

    Optional<AccountHolder> findByUserName(String userName);
}
