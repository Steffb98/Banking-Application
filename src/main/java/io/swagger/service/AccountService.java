package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final int IBAN_FORMAT_CHARACTERS = 18;
    private final int USERID_FORMAT_CHARACTERS = 6;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
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
}
