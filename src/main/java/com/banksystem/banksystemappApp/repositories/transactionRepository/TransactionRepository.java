package com.banksystem.banksystemappApp.repositories.transactionRepository;

import com.banksystem.banksystemappApp.models.transaction.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
