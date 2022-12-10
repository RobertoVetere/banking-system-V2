package com.banksystem.banksystemappApp.repositories.bankRepository;

import com.banksystem.banksystemappApp.models.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
