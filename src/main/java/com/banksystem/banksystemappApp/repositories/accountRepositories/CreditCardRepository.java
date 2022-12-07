package com.banksystem.banksystemappApp.repositories.accountRepositories;

import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {

}
