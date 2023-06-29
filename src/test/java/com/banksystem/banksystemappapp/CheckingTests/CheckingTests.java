package com.banksystem.banksystemappapp.CheckingTests;

import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.accounts.Checking;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.Address;
import com.banksystem.banksystemappapp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
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
public class CheckingTests {

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;


    @BeforeEach
    void setUp(){
        AccountHolder primaryOwner = new AccountHolder("Marta Perez","user1", "1234",LocalDate.of(1985, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        AccountHolder secondaryOwner2 = new AccountHolder("Roberto Vetere","user2","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner2));
        Checking account = new Checking(new BigDecimal("24523.00"),"1234L",primaryOwner,secondaryOwner2,
                new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);

        checkingRepository.save(account);

    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void shouldSaveCheckingAccount_OK(){

        Checking account = checkingRepository.findAll().get(0);

        assertEquals(new BigDecimal("24523.00"),account.getBalance());
    }

    @Test
    void shouldSaving_PrimaryAndSecondaryOwnerAssign_OK(){

        String primaryOwner = checkingRepository.findAll().get(0).getPrimaryOwner().getName();
        String secondaryOwner = checkingRepository.findAll().get(0).getSecondaryOwner().getName();

        assertEquals("Marta Perez",checkingRepository.findAll().get(0).getPrimaryOwner().getName());
        assertEquals("Roberto Vetere",checkingRepository.findAll().get(0).getSecondaryOwner().getName());
    }

    @Test
    void shouldSetMinimumBalanceDefault_OK(){

        Checking checking = new Checking(new BigDecimal("24523.00"),"1234L",null,null,
                 AccountType.CHECKING);
        checkingRepository.save(checking);

        assertEquals(new BigDecimal("250.00"),checkingRepository.findAll().get(1).getMinimumBalance());
    }

    @Test
    void shouldSetMonthlyMaintenanceFee_OK(){
        Checking checking = new Checking(new BigDecimal("24523.00"),"1234L",null,null,
                 AccountType.CHECKING);
        checkingRepository.save(checking);

        assertEquals(new BigDecimal("12.00"),checkingRepository.findAll().get(1).getMonthlyMaintenanceFee());
    }
}
