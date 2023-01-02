package com.banksystem.banksystemappApp.controllerTests;


import com.banksystem.banksystemappApp.CreditCardTests.CreditCardControllerTests;
import com.banksystem.banksystemappApp.controllers.DTO.AccountDTO;
import com.banksystem.banksystemappApp.controllers.DTO.TransactionDTO;
import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.Checking;
import com.banksystem.banksystemappApp.models.accounts.CreditCard;
import com.banksystem.banksystemappApp.models.accounts.Savings;
import com.banksystem.banksystemappApp.models.transaction.Transaction;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Address;
import com.banksystem.banksystemappApp.models.users.Admin;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappApp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappApp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AdminRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.ThirdPartyRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.banksystem.banksystemappApp.enums.TransactionType.TRANSFER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class AccountHolderControllerTests {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();


    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    ThirdPartyRepository thirdPartyRepository;
    @Autowired
    AccountHolderRepository accountHolderRepository;

    @Autowired
    CheckingRepository checkingRepository;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    SavingsRepository savingsRepository;

    @Autowired
    CreditCardRepository creditCardRepository;

    AccountHolder primaryOwner;

    AccountHolder secondaryOwner2;
    Savings account3;

    Savings account4;

    Checking account;

    Checking account2;

    Transaction transaction;

    TransactionDTO transaction2;
    Admin admin;

    CreditCard creditCard1;


    @BeforeEach
    void setUp() {
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();


        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.findAndRegisterModules();

        admin = new Admin("Marcos","admin","1234");
        adminRepository.save(admin);
        primaryOwner  = new AccountHolder("Marta Perez","Perez","1234",LocalDate.of(2000, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        secondaryOwner2 = new AccountHolder("Roberto Vetere","admin3","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));

        accountHolderRepository.saveAll(List.of(primaryOwner, secondaryOwner2));

        account = new Checking(new BigDecimal("2452300.00"),"1234L",primaryOwner,primaryOwner
                ,new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);

        account2 = new Checking(new BigDecimal("2452300.00"),"1234L",secondaryOwner2,secondaryOwner2
                ,new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);
        checkingRepository.saveAll(List.of(account,account2));

        account3 = new Savings(new BigDecimal("12456.00"),"4567L",secondaryOwner2,secondaryOwner2,
                new BigDecimal("100.0"),0.15,AccountType.SAVINGS);

        account4 = new Savings(new BigDecimal("12456.00"),"4567L",secondaryOwner2,secondaryOwner2,
                new BigDecimal("100.0"),0.15,AccountType.SAVINGS);
        savingsRepository.save(account4);

        savingsRepository.save(account3);

        creditCard1 = new CreditCard(new BigDecimal("100000.00"),"1234",secondaryOwner2,secondaryOwner2,new BigDecimal("1000.00"),new BigDecimal("0.20"),AccountType.CREDITCARD);
        creditCardRepository.save(creditCard1);


    }

    @AfterEach
    void tearUp() {
        transactionRepository.deleteAll();
    }


    //
    @Test
    @WithMockUser(username = "Perez", password = "1234")
    void makeTransfer_OK() throws Exception {

        transaction  = new Transaction(account.getAccountNumber(),account2.getAccountNumber(),
                account,account2,account2.getPrimaryOwner().getName(),new BigDecimal("2452300.00"),TRANSFER);
        transactionRepository.save(transaction);

        String body = objectMapper.writeValueAsString(transaction);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/account-holder/transfer/"))
                .andExpect(status().isAccepted()).andReturn();
       System.err.println(result.getResolvedException().getMessage());
        //System.err.println(result.getResolvedException().getMessage());
        //System.out.println(result.getResponse().getContentAsString());
       assertTrue(result.getResponse().getContentAsString().contains(transaction.getAmount().toString()));

    }

    @Test
    @WithMockUser(username = "Perez", password = "1234")
    void get_balance_OK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/account-holder/balance/1"))
                .andExpect(status().isOk()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains(account.getBalance().toString()));

    }

    @Test
    @WithMockUser(username = "admin3", password = "1234")
    void saving_balance_OK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/account-holder/savings-balance/" + account3.getId()).param("secretKey", "1234"))
                .andExpect(status().isOk()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(account3.getBalance().toString()));

    }

    @Test
    @WithMockUser(username = "Perez", password = "1234")
    void deposit_OK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/account-holder/deposit/" + account.getId()).param("deposit", "100000"))
                .andExpect(status().isAccepted()).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("2552300.00"));

    }
    @Test
    @WithMockUser(username = "admin3", password = "1234")
    void withdrawal_OK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.patch("/account-holder/withdrawal/" + account4.getId()).param("withdrawal", "100"))
                .andExpect(status().isAccepted()).andReturn();
        System.out.println(result.getResponse().getContentAsString());
        assertTrue(result.getResponse().getContentAsString().contains("12356.00"));

    }

    @Test
    @WithMockUser(username = "admin3", password = "1234")
    void creditCardBalance_OK() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/account-holder/credit-card/" + creditCard1.getId()).param("secretKey", "1234"))
                .andExpect(status().isUnauthorized()).andReturn();
        //System.err.println(result.getResolvedException().getMessage());
        //System.out.println(result.getResponse().getContentAsString());
       // assertTrue(result.getResponse().getContentAsString().contains("2452300.00"));

    }


}
