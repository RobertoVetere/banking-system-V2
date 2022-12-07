package com.banksystem.banksystemappApp.repositories.accountRepositories;

import com.banksystem.banksystemappApp.models.accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
