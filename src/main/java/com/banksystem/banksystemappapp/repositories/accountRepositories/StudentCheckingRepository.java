package com.banksystem.banksystemappapp.repositories.accountRepositories;

import com.banksystem.banksystemappapp.models.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
}
