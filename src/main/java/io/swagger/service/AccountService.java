package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
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
        if (account == null){
            throw new NotFoundException(404, "No account found with this iban");
        }
        return account;
    }

    public List<Account> getAccountsByUserId(Long userId) throws BadInputException, NotFoundException {
        if (userId.toString().length() != USERID_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of userid is incorrect");
        }
        List<Account> accounts = accountRepository.findAccountsByUserid(userId);
        if (accounts.isEmpty()){
            throw new NotFoundException(404, "No accounts found with this userid");
        }
        return accounts;
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

