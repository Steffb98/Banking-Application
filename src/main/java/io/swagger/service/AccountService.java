package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final int IBAN_FORMAT_CHARACTERS = 18;
    private final int USERID_FORMAT_CHARACTERS = 6;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository){
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public Account getAccountByIban(String iban) throws NotFoundException, BadInputException {
        if (iban.length() != IBAN_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of iban is incorrect");
        }

        Account account = accountRepository.findAccountByIban(iban);

        if (account == null) {
            throw new NotFoundException(404, "No account found with this iban");
        }

        return account;
    }

    public List<Account> getAccountsByUserId(Long userId) throws BadInputException, NotFoundException {
        if (userId.toString().length() != USERID_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of userid is incorrect");
        }

        List<Account> accounts = accountRepository.findAccountsByUserid(userId);

        if (accounts.isEmpty()) {
            throw new NotFoundException(404, "No accounts found with this userid");
        }

        return accounts;
    }

    public void toggleActivityStatus(String iban) throws NotFoundException, BadInputException {
        if (iban.length() != IBAN_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of iban is incorrect");
        }

        //retrieving account from database to use the built-in security from h2o
        Account account = accountRepository.findAccountByIban(iban);

        if (account == null) {
            throw new NotFoundException(404, "No account found with this iban");
        }

        //setting isActive to the opposite of the current value
        account.setIsactive(!account.getIsactive());

        accountRepository.save(account);
    }

    public void createAccount(Account acc) throws NotFoundException {
        if (userRepository.findUserById(acc.getUserid()) == null){
            throw new NotFoundException(404, "User not found");
        }

        Account newAcc = new Account(generateIban(), acc.getTypeofaccount(), acc.getUserid());

        accountRepository.save(newAcc);

        System.out.println(newAcc);
    }

    public String generateIban(){
        while(true){
            Random rnd = new Random();
            int min = 01;
            int max = 99;
            int generatedNumber = rnd.nextInt(max - min) + min;

            String generatedIban = "NL" + String.format("%02d", generatedNumber) + "INHO";

            min = 100000000;
            max = 999999999;

            generatedNumber = rnd.nextInt(max - min) + min;

            generatedIban += "0" + generatedNumber;

            if (accountRepository.findAccountByIban(generatedIban) == null){
                return generatedIban;
            }
        }
    }
}
