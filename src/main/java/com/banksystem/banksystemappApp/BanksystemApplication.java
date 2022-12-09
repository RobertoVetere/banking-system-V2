package com.banksystem.banksystemappApp;

import com.banksystem.banksystemappApp.enums.AccountType;
import com.banksystem.banksystemappApp.models.accounts.*;
import com.banksystem.banksystemappApp.models.users.AccountHolder;
import com.banksystem.banksystemappApp.models.users.Address;
import com.banksystem.banksystemappApp.models.users.ThirdParty;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CreditCardRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.AccountHolderRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.CheckingRepository;
import com.banksystem.banksystemappApp.repositories.accountRepositories.SavingsRepository;
import com.banksystem.banksystemappApp.repositories.userRepositories.ThirdPartyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
public class BanksystemApplication implements CommandLineRunner {

	@Autowired
	AccountHolderRepository accountHolderRepository;

	@Autowired
	SavingsRepository savingsRepository;

	@Autowired
	CheckingRepository checkingRepository;

	@Autowired
	CreditCardRepository creditCardRepository;

	@Autowired
	ThirdPartyRepository thirdPartyRepository;

	public static void main(String[] args) {
		SpringApplication.run(BanksystemApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		//AccountHolder.accountHolderAgeValidate(LocalDate.of(1985, 1, 8));
		//AccountHolder.accountHolderAgeValidate(LocalDate.of(2018, 1, 8));

		AccountHolder BankOfNeverland = new AccountHolder("Bank Of Neverland","1234",LocalDate.of(1980, 3, 25),
				new Address("carrer Sant Beatle",87562,"Tenerife","Spain"),
				new Address("calle Jardín Bogdanico",45638,"Madrid","Spain"));

		AccountHolder primaryOwner = new AccountHolder("Marta Perez","1234",LocalDate.of(2000, 1, 8),
				new Address("carrer Sant Andrew",8030,"Madrid","Spain"),
				new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

		AccountHolder secondaryOwner2 = new AccountHolder("Roberto Vetere","1234",LocalDate.of(1985, 9, 21),
				new Address("carrer la nada",28850,"Madrid","Spain"),
				new Address("carrer Arquimedes",28504,"Barcelona","Spain"));

		AccountHolder primaryOwner2 = new AccountHolder("Antonio Roman","1234",LocalDate.of(1987, 3, 18),
				new Address("Calle de Enmedio",28850,"Torrejón","Spain"),
				new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

		AccountHolder primaryOwner3 = new AccountHolder("Javier Alonso","1234",LocalDate.of(2000, 3, 18),
				new Address("Calle de Enmedio",28850,"Torrejón","Spain"),
				new Address("carrer Sant Andrew",8030,"Madrid","Spain"));

		accountHolderRepository.saveAll(List.of(primaryOwner, primaryOwner2,secondaryOwner2,primaryOwner3,BankOfNeverland));

		Checking account = new Checking(new BigDecimal("2452300.00"),"1234L",primaryOwner,null
				,new BigDecimal("100.0"),new BigDecimal("3.0"), AccountType.CHECKING);

		StudentChecking StudentAccount = new StudentChecking(new BigDecimal("24523.0"),"1234L",
				primaryOwner2,secondaryOwner2, AccountType.STUDENTCHECKING);

		Checking account2 = new Checking(new BigDecimal("0.00"),"4567L",primaryOwner2,null,
				new BigDecimal("100.0"),new BigDecimal("3.0"),AccountType.CHECKING);

		checkingRepository.saveAll(List.of(account,account2));

		Savings account3 = new Savings(new BigDecimal("12456.0"),"4567L",primaryOwner2,null,
				new BigDecimal("100.0"),0.15,AccountType.SAVINGS);

		savingsRepository.saveAll(List.of(account3));

		CreditCard creditCard1 = new CreditCard(new BigDecimal("1800.0"),"789L",primaryOwner,null,
				new BigDecimal("112000.00"),new BigDecimal("0.05"),AccountType.CREDITCARD);

		creditCardRepository.saveAll(List.of(creditCard1));

		Checking bankAccount = new Checking(new BigDecimal("8000000000.00"),"1234L",BankOfNeverland,null
				,AccountType.CHECKING);
		checkingRepository.save(bankAccount);

		ThirdParty thirdParty = new ThirdParty("Third-Party-0001","12345679");
		thirdPartyRepository.save(thirdParty);

		Checking thirdPartyAccount = new Checking(new BigDecimal("0"),"12345679L",null,null,AccountType.CHECKING);







	}
}
