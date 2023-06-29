package com.banksystem.banksystemappapp.CreditCardTests;

import com.banksystem.banksystemappapp.models.accounts.CreditCard;
import com.banksystem.banksystemappapp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappapp.repositories.userRepositories.AccountHolderRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class CreditCardControllerTests {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Autowired
    AccountHolderRepository accountHolderRepository;

    CreditCard creditCard;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp(){

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

    }
/*
    @Test
    void create_creditCard_OK() throws Exception {
        AccountHolder primaryOwner = new AccountHolder("Marta Perez", LocalDate.of(1985, 1, 8),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
                new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

        AccountHolder secondaryOwner2 = new AccountHolder("Roberto Vetere",LocalDate.of(1985, 9, 21),
                new Address("carrer la nada",28850,"Madrid","Spain"),
                new Address("carrer Arquimedes",28504,"Barcelona","Spain"));
        accountHolderRepository.saveAll(List.of(primaryOwner,secondaryOwner2));

        CreditCard creditCard1 = new CreditCard(new BigDecimal("1800.00"),789L,primaryOwner,null,
                new BigDecimal("20.0"),new BigDecimal("112000.00"),new BigDecimal("0.05"));

        String body = objectMapper.writeValueAsString(creditCard1);
        MvcResult result = mockMvc.perform(post("/create-credit-card")
                .content(body).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated()).andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Roberto Vetere"));
    }

 */

}
