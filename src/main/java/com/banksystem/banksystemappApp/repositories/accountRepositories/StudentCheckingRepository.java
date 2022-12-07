package com.banksystem.banksystemappApp.repositories.accountRepositories;

import com.banksystem.banksystemappApp.models.accounts.StudentChecking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentCheckingRepository extends JpaRepository<StudentChecking, Long> {
}
