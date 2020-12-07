package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.LimitReachedException;
import io.swagger.exception.NotAuthorizedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.model.User;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountService accountService;
    private UserService userService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, UserService userService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.userService = userService;
    }

    public void checkTransactionAuthorization(Transaction transaction) throws NotAuthorizedException {
        Object security = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        //TODO: Fix when receivinguserid is in transaction model
        if (((User) security).getuserId().equals(transaction.getPerforminguser()) || ((User) security).getAuthorities().contains(new SimpleGrantedAuthority("ROLE_EMPLOYEE"))){
            return;
        }

        throw new NotAuthorizedException(401, "not authorized");
    }

    @Transactional(rollbackOn = Exception.class)
    public void addTransaction(Transaction transaction) throws BadInputException, NotFoundException, LimitReachedException, NotAuthorizedException {

        if (accountService.getAccountByIban(transaction.getSender()).getTransactionlimit().compareTo(transaction.getAmount()) <= 0){
            throw new BadInputException(401, "Amount exceed the transactionlimit");
        }

        transaction.setDate(OffsetDateTime.now());
        accountService.updateBalance(accountService.getAccountByIban(transaction.getSender()), transaction.getAmount(), AccountService.TypeOfTransactionEnum.SUBTRACT);
        accountService.updateBalance(accountService.getAccountByIban(transaction.getReceiver()), transaction.getAmount(), AccountService.TypeOfTransactionEnum.ADD);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) throws NotFoundException, NotAuthorizedException {
        Transaction transaction = transactionRepository.findTransactionById(id);
        checkTransactionAuthorization(transaction);
        if ( transaction == null) {
            throw new NotFoundException(404, "No transaction found with this id");
        }

        return transaction;
    }

    public List<Transaction> getAllTransactionsFromUser(Long userId) throws NotFoundException, NotAuthorizedException {

        // checks for valid userId
        userService.getUserById(userId);

        List<Transaction> transactions = transactionRepository.getAllByPerforminguserOrderByDate(userId);

        if (transactions.isEmpty()){
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }

    public List<Transaction> getAllTransactionsFromAccount(String accountId) throws NotFoundException, BadInputException, NotAuthorizedException {

        // checks for valid iban
        accountService.getAccountByIban(accountId);

        List<Transaction> transactions = transactionRepository.findTransactionByReceiverOrSenderOrderByDate(accountId, accountId);

        if (transactions.isEmpty()){
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }
}
