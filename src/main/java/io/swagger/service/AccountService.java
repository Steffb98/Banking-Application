package io.swagger.service;

import io.swagger.exception.*;
import io.swagger.model.Account;
import io.swagger.repository.AccountRepository;
import io.swagger.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Random;

import static io.swagger.model.Account.TypeofaccountEnum.BANK;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;
    private final AuthorizationService authorizationService;
    private final int IBAN_FORMAT_CHARACTERS = 18;
    private final int USERID_FORMAT_CHARACTERS = 6;

    @Autowired
    public AccountService(AccountRepository accountRepository, UserRepository userRepository, AuthorizationService authorizationService) {
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
        this.authorizationService = authorizationService;
    }

    public Account getAccountByIban(String iban) throws NotFoundException, BadInputException, NotAuthorizedException, ForbiddenException {
        Account account = accountChecks(iban);

        authorizationService.checkAccountAuthorization(account);

        return account;
    }

    private Account accountChecks(String iban) throws BadInputException, NotFoundException, ForbiddenException {
        if (iban.length() != IBAN_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of iban is incorrect");
        }

        Account account = accountRepository.findAccountByIban(iban);

        if (account == null) {
            throw new NotFoundException(404, "No account found with this iban");
        }

        if (account.getTypeofaccount() == BANK) {
            throw new ForbiddenException(403, "can't access this account");
        }
        return account;
    }

    public List<Account> getAccountsByUserId(Long userId) throws BadInputException, NotFoundException, NotAuthorizedException {
        authorizationService.checkUserAuthorization(userId);
        if (userId.toString().length() != USERID_FORMAT_CHARACTERS) {
            throw new BadInputException(400, "Format of userid is incorrect");
        }

        List<Account> accounts = accountRepository.findAccountsByUserid(userId);

        if (accounts.isEmpty()) {
            throw new NotFoundException(404, "No accounts found with this userid");
        }

        return accounts;
    }

    public void toggleActivityStatus(String iban) throws NotFoundException, BadInputException, ForbiddenException {
        Account account = accountChecks(iban);

        //setting isActive to the opposite of the current value
        account.setIsactive(!account.getIsactive());

        accountRepository.save(account);
    }

    public void createAccount(Account acc) throws NotFoundException {
        if (userRepository.findUserByUserId(acc.getUserid()) == null) {
            throw new NotFoundException(404, "User not found");
        }

        Account newAcc = new Account(generateIban(), acc.getTypeofaccount(), acc.getUserid());

        accountRepository.save(newAcc);
    }

    public String generateIban() {
        while (true) {
            Random rnd = new Random();
            int min = 02;
            int max = 99;
            int generatedNumber = rnd.nextInt(max - min) + min;

            String generatedIban = "NL" + String.format("%02d", generatedNumber) + "INHO";

            min = 100000000;
            max = 999999999;

            generatedNumber = rnd.nextInt(max - min) + min;

            generatedIban += "0" + generatedNumber;

            if (accountRepository.findAccountByIban(generatedIban) == null) {
                return generatedIban;
            }
        }
    }

    public List<Account> getAllAccounts() {
        return (List<Account>) accountRepository.findAll();
    }

    public Account getAccountFromUserIdWhereTypeOfAccountEquals(Long userId, Account.TypeofaccountEnum typeOfAccount) {
        return accountRepository.findAccountByUseridAndTypeofaccountEquals(userId, typeOfAccount);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public Boolean checkDayLimit(Account account) {
        if (account.getNumberoftransactions() < account.getDaylimit()) {
            account.setNumberoftransactions(account.getNumberoftransactions() + 1);
            accountRepository.save(account);
            return false;
        } else {
            return true;
        }
    }

    public Boolean checkBalance(Account account, BigDecimal amount) {
        return account.getBalance().subtract(amount).compareTo(account.getAbsolutlimit()) < 0;
    }

    public void updateBalance(Account account, BigDecimal amount, TypeOfTransactionEnum typeOfTransactionEnum) throws LimitReachedException {

        if (typeOfTransactionEnum == TypeOfTransactionEnum.ADD) {
            account.setBalance(account.getBalance().add(amount));
        } else {
            if (checkDayLimit(account)) {
                throw new LimitReachedException(429, "You have reached your daily limit");
            }
            if (checkBalance(account, amount)) {
                throw new LimitReachedException(429, "You don't have enough money on your account");
            }
            account.setBalance(account.getBalance().subtract(amount));
        }
        accountRepository.save(account);
    }

    public enum TypeOfTransactionEnum {
        ADD, SUBTRACT
    }
}

