package com.banksystem.banksystemappApp.repositories.userRepositories;

import com.banksystem.banksystemappApp.models.users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
