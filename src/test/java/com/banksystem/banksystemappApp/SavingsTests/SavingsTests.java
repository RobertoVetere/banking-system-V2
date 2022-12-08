package com.banksystem.banksystemappApp.SavingsTests;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Address;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SavingsTests {

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    @BeforeEach
    void setUp(){

        AccountHolder primaryOwner = new AccountHolder("Marta Perez","1234", LocalDate.of(1985, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        AccountHolder secondaryOwner2 = new AccountHolder("Roberto Vetere","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner2));

        Savings saving = new Savings(new BigDecimal("1800.00"),1234L,primaryOwner,secondaryOwner2,
                new BigDecimal("120.00"),0.70, AccountType.SAVINGS);
        savingsRepository.save(saving);


    }

    @AfterEach
    void tearDown() {
        savingsRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void shouldSaving_WhenSaved_OK(){

        Savings saving = savingsRepository.findAll().get(0);

        assertEquals(new BigDecimal("1800.00"),saving.getBalance());
        }

    @Test
    void shouldSaving_PrimaryAndSecondaryOwnerAssign_OK(){

        String primaryOwner = savingsRepository.findAll().get(0).getPrimaryOwner().getName();
        String secondaryOwner = savingsRepository.findAll().get(0).getSecondaryOwner().getName();

        assertEquals("Marta Perez",savingsRepository.findAll().get(0).getPrimaryOwner().getName());
        assertEquals("Roberto Vetere",savingsRepository.findAll().get(0).getSecondaryOwner().getName());
    }


    @Test
    void shouldSetInterestRateAuto_IfLimitExceeded_OK(){
        Savings saving2 = new Savings(new BigDecimal("1800.00"),1234L,null,null,
                new BigDecimal("120.00"),0.70, AccountType.SAVINGS);
        savingsRepository.saveAll(List.of(saving2));

        assertEquals(0.50,savingsRepository.findAll().get(1).getInterestRate());
    }

    @Test
    void shouldSetMinimumBalanceAuto_IfLimitExceeded_OK(){
        Savings saving2 = new Savings(new BigDecimal("1800.00"),1234L,null,null,
                new BigDecimal("50.00"),0.70, AccountType.SAVINGS);
        savingsRepository.saveAll(List.of(saving2));

        assertEquals(new BigDecimal("100.00"),savingsRepository.findAll().get(1).getMinimumBalance());
    }


    @Test
    void shouldSetInterestRateDefault_OK(){
        Savings saving2 = new Savings(new BigDecimal("1800.00"),1234L,null,null,
                 AccountType.SAVINGS);
        savingsRepository.saveAll(List.of(saving2));

        assertEquals(0.0025,savingsRepository.findAll().get(1).getInterestRate());
    }

    @Test
    void shouldSetBalanceLessThan1000_OK(){
        Savings saving2 = new Savings(new BigDecimal("800.00"),1234L,null,null,
                 AccountType.SAVINGS);
        savingsRepository.saveAll(List.of(saving2));

        assertEquals(new BigDecimal("800.00"),savingsRepository.findAll().get(1).getBalance());
    }

    @Test
    void shouldSetMinimumBalanceDefault_OK(){
        Savings saving2 = new Savings(new BigDecimal("1800.00"),1234L,null,null,
                 AccountType.SAVINGS);
        savingsRepository.saveAll(List.of(saving2));

        assertEquals(new BigDecimal("1000.00"),savingsRepository.findAll().get(1).getMinimumBalance());
    }



}
