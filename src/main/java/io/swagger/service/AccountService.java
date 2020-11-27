package io.swagger.service;

import io.swagger.api.NotFoundException;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    private final AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public Account getAccountByIban(String iban) throws NotFoundException {
        Account account = accountRepository.findAccountByIban(iban);
        if (account == null){
            throw new NotFoundException(404, "No account found with this iban");
        }
        return account;
    }
}
