package com.banksystem.banksystemappapp.controllerTests;


import com.banksystem.banksystemappapp.dtos.AccountDTO;
import com.banksystem.banksystemappapp.enums.AccountType;
import com.banksystem.banksystemappapp.models.accounts.Checking;
import com.banksystem.banksystemappapp.models.transaction.Transaction;
import com.banksystem.banksystemappapp.models.users.AccountHolder;
import com.banksystem.banksystemappapp.models.users.Address;
import com.banksystem.banksystemappapp.models.users.Admin;
import com.banksystem.banksystemappapp.models.users.ThirdParty;
import com.banksystem.banksystemappapp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.RoleRepository;
import com.banksystem.banksystemappapp.repositories.securityRepository.UserRepository;
import com.banksystem.banksystemappapp.repositories.transactionRepository.TransactionRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AdminRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.ThirdPartyRepository;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static com.banksystem.banksystemappapp.enums.TransactionType.TRANSFER;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
public class ControllerTests {

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

    AccountHolder primaryOwner;

    AccountHolder secondaryOwner2;

    Checking account;

    Checking account2;

    Transaction transaction;

    ThirdParty thirdParty;

    Admin admin;

    @BeforeEach
    void setUp() {
        accountHolderRepository.deleteAll();
        thirdPartyRepository.deleteAll();
        roleRepository.deleteAll();
        userRepository.deleteAll();


        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        objectMapper.findAndRegisterModules();

         primaryOwner = new AccountHolder("Marta Perez","Perez","1234",LocalDate.of(2000, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        secondaryOwner2 = new AccountHolder("Roberto Vetere","admin3","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));

        accountHolderRepository.saveAll(List.of(primaryOwner, secondaryOwner2));

        account = new Checking(new BigDecimal("2452300.00"),"1234L",primaryOwner,null
                ,new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);

        account2 = new Checking(new BigDecimal("2452300.00"),"1234L",secondaryOwner2,null
                ,new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);
        checkingRepository.saveAll(List.of(account,account2));

        transaction = new Transaction(account.getAccountNumber(),account2.getAccountNumber(),account,account2,account2.getPrimaryOwner().getName(),new BigDecimal("2452300.00"),TRANSFER);
        transactionRepository.save(transaction);

        admin = new Admin( "alfredo", "alfredo", "alfredo");
        adminRepository.save(admin);

        thirdParty = new ThirdParty("Datafono12","thirdParty12","123456789","1234");
        thirdPartyRepository.save(thirdParty);

    }

    @AfterEach
    void tearUp() {
        transactionRepository.deleteAll();
    }



    //Este test da error 404
    @Test
    @WithMockUser(username = "alfredo", password = "alfredo")
    void createThirdParty() throws Exception {

        String body = objectMapper.writeValueAsString(thirdParty);
        MvcResult result = mockMvc.perform(post("/admin/add-third-party/").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Datafono12"));
    }

    //Este test funciona
    @Test
    @WithMockUser(username = "Josue", password = "1234")
    void createAccount() throws Exception {

        AccountHolder primaryOwner25 = new AccountHolder("Josue Perez","Josue","1234",LocalDate.of(2000, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        AccountHolder secondaryOwner226 = new AccountHolder("Perez Vetere","123456","1234",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));

        accountHolderRepository.saveAll(List.of(primaryOwner25, secondaryOwner226));

        AccountDTO account = new AccountDTO(3L,4L,new BigDecimal("1234"),"1234",new BigDecimal("1234"),new BigDecimal("0.20"));

        String body = objectMapper.writeValueAsString(account);

        MvcResult result = mockMvc.perform(post("/admin/add-checking").content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Josue Perez"));
        assertTrue(result.getResponse().getContentAsString().contains("carrer la nada"));
        assertTrue(result.getResponse().getContentAsString().contains("Barcelona"));
    }

}
