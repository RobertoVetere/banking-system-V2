package com.banksystem.banksystemappApp.CreditCardTests;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Address;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
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
public class CreditCardTest {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    CreditCard creditCard;

    @BeforeEach
    void setUp(){

        AccountHolder primaryOwner = new AccountHolder("Marta Perez","1234","user3", LocalDate.of(1985, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        AccountHolder secondaryOwner2 = new AccountHolder("Roberto Vetere","user4","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner2));

        CreditCard creditCard1 = new CreditCard(new BigDecimal("1800.00"),"789L",primaryOwner,secondaryOwner2,
                new BigDecimal("112000.00"),new BigDecimal("0.05"), AccountType.CREDITCARD);

        creditCardRepository.saveAll(List.of(creditCard1));
    }

    @AfterEach
    void tearDown() {
        creditCardRepository.deleteAll();
        accountHolderRepository.deleteAll();
    }

    @Test
    void shouldCreateNewCreditCard_WhenSaved(){
        CreditCard creditCard1 = creditCardRepository.findAll().get(0);
        String primaryOwner = creditCardRepository.findAll().get(0).getPrimaryOwner().getName();

        assertEquals(new BigDecimal("1800.00"),creditCard1.getBalance());
        assertEquals("Marta Perez",creditCardRepository.findAll().get(0).getPrimaryOwner().getName());
    }

    @Test
    void shouldSaving_PrimaryAndSecondaryOwnerAssign_OK(){

        String primaryOwner = creditCardRepository.findAll().get(0).getPrimaryOwner().getName();
        String secondaryOwner = creditCardRepository.findAll().get(0).getSecondaryOwner().getName();

        assertEquals("Marta Perez",creditCardRepository.findAll().get(0).getPrimaryOwner().getName());
        assertEquals("Roberto Vetere",creditCardRepository.findAll().get(0).getSecondaryOwner().getName());
    }

    @Test
    void shouldSetDefaultCreditLimit100_OK(){
        CreditCard creditCard2 = new CreditCard(new BigDecimal("1800.00"),"789L",null,null,new BigDecimal("100.00"),new BigDecimal("0.20"),
                 AccountType.CREDITCARD);
        creditCardRepository.save(creditCard2);

        assertEquals(new BigDecimal("100.00"),creditCardRepository.findAll().get(1).getCreditLimit());
    }

    @Test
    void shouldSetDefaulInteresRate_OK(){
        CreditCard creditCard2 = new CreditCard(new BigDecimal("1800.00"),"789L",null,null,new BigDecimal("100.00"),new BigDecimal("0.20"),
                AccountType.CREDITCARD);
        creditCardRepository.save(creditCard2);

        assertEquals(new BigDecimal("0.20"),creditCardRepository.findAll().get(1).getInterestRate());
    }
    @Test
    void shouldSetMaximumCreditAuto_IfLimitExceeded_OK(){
        CreditCard creditCard2 = new CreditCard(new BigDecimal("1800.00"),"789L",null,null,
                new BigDecimal("112000.00"),new BigDecimal("0.05"), AccountType.CREDITCARD);
        creditCardRepository.saveAll(List.of(creditCard2));

        assertEquals(new BigDecimal("100000.00"),creditCardRepository.findAll().get(1).getCreditLimit());
    }

    @Test
    void shouldSetInterestRateAuto_IfLimitExceeded_OK(){
        CreditCard creditCard2 = new CreditCard(new BigDecimal("1800.00"),"789L",null,null,
                new BigDecimal("112000.00"),new BigDecimal("0.05"), AccountType.CREDITCARD);
        creditCardRepository.saveAll(List.of(creditCard2));

        assertEquals(new BigDecimal("0.10"),creditCardRepository.findAll().get(1).getInterestRate());
    }
}
