package io.swagger.service;

import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class AccountService {
    private AccountRepository accountRepository;

    @Autowired
    public AccountService(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    public List<Account> getAllAccounts(){ return (List<Account>) accountRepository.findAll(); }

    public void updateAccount(Account account){ accountRepository.save(account); }

    public Boolean checkDayLimit(Account account) {
        if (account.getNumberoftransactions() < account.getDaylimit()) {
            account.setNumberoftransactions(account.getNumberoftransactions() + 1);
            accountRepository.save(account);
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkBalance(Account account, BigDecimal amount){
        if (account.getBalance().subtract(amount).compareTo(account.getAbsolutlimit()) == -1 ){
            return true;
        } else {
            return false;
        }
    }

    public void updateBalance(Account account, BigDecimal amount, TypeOfTransactionEnum typeOfTransactionEnum){
        switch (typeOfTransactionEnum) {
            case ADD:
                account.setBalance(account.getBalance().add(amount));
                accountRepository.save(account);
                break;
            case SUBTRACT:
                account.setBalance(account.getBalance().subtract(amount));
                accountRepository.save(account);
                break;
        }
    }

    enum TypeOfTransactionEnum {
        ADD("add"), SUBTRACT("subtract");

        private String value;

        TypeOfTransactionEnum(String value){this.value = value;}

        public String getStringValue() {return value;}
    }
}

