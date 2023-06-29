package com.banksystem.banksystemappapp.repositories.bankRepository;

import com.banksystem.banksystemappapp.models.bank.Bank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankRepository extends JpaRepository<Bank,Long> {
}
