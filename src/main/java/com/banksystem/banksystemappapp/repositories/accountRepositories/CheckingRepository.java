package com.banksystem.banksystemappapp.repositories.accountRepositories;

import com.banksystem.banksystemappapp.models.accounts.Checking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CheckingRepository extends JpaRepository<Checking, Long> {
}
