# Banking System 
![cover](https://github.com/RobertoVetere/banking-system-V2/blob/main/screenshots/bankofneverland.png)


# Spring boot REST API
Final bootcamp project demonstrating the use of Java and Spring-boot to build a microservice to be used by an online bank.

Technologies and Dependencies

- MySQL Driver
- Spring Data JPA
- Spring Web
- Spring Boot DevTools
- Spring Security
- Project lombok

## Running locally 
```
localhost:8080/
```
Remember to change the password in application.properties
Some sample screenshots are available to help you test the api in Postman.

![Create Account](screenshots/properties.PNG)

The database is included in the "dataBase" folder of the project under the name "banksystem".

For convenience it includes some examples of accounts and users ready for testing.

Routes are secured, passwords are encrypted when creating Accounts and Users. Remember them to be able to authorise requests in Postman.

## Testing
Import the "postman end points collection" file into the application.

### How to test
1. Create Bank
   > Use create account API to create an account by providing a `name` and `userName`
   >
   ![Create Account](screenshots/CreateBank.PNG)

2. Create admin account
   ![Create Account](screenshots/CreateNewAdmin.PNG)

2. Create new account holder
   >Use noted `accountNumber` as `targetAccountNo` and provide amount greater than zero to deposit cash into an account

   ![createAccountHolder](screenshots/CreateNewAccountHolder.PNG)

3. Choose an account type and fill in the details

   ![newAccount](screenshots/CreateNewAccount.PNG)

4. Delete Account
   >Try the route to delete accounts

   ![Check Balance](screenshots/DeleteAccount.PNG)

5. Check Balance
   >Use noted `accountNumber` and `sortCode` to check account balance

   ![Check Balance](screenshots/check_balance.png)

4. Withdraw Cash
   >Use noted `accountNumber` and `sortCode` and `amount` grater than zero to withdraw cash from an account

   ![Withdraw cash](screenshots/withdraw.png)

5. Check Balance again to verify withdrawal

   ![Check Balance](screenshots/check_balance_2.png)



### Author
<li><a href="https://github.com/RobertoVetere">Roberto Vetere</a></li>
