package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.threeten.bp.OffsetDateTime;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {
    private TransactionRepository transactionRepository;
    private AccountService accountService;


    public TransactionService(TransactionRepository transactionRepository, AccountService accountService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
    }

    public void addTransaction(Transaction transaction) throws BadInputException, NotFoundException{
        transaction.setDate(OffsetDateTime.now());
        accountService.updateBalance(accountService.getAccountByIban(transaction.getSender()), transaction.getAmount(), AccountService.TypeOfTransactionEnum.SUBTRACT);
        accountService.updateBalance(accountService.getAccountByIban(transaction.getReceiver()), transaction.getAmount(), AccountService.TypeOfTransactionEnum.ADD);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) throws NotFoundException {

        Transaction transaction = transactionRepository.findTransactionById(id);

        if ( transaction == null) {
            throw new NotFoundException(404, "No transaction found with this id");
        }

        return transaction;
    }

    public List<Transaction> getAllTransactionsFromUser(Long userId) { return (List<Transaction>) transactionRepository.getAllByPerforminguserOrderByDate(userId); }

    public List<Transaction> getAllTransactionsFromAccount(String accountId) throws NotFoundException, BadInputException {

        // checks for valid iban
        accountService.getAccountByIban(accountId);

        List<Transaction> transactions = (List<Transaction>)transactionRepository.getTransactionsByAccount(accountId);

        if (transactions == null){
            throw new NotFoundException(404, "There are no transactions found for this account");
        }
        return transactions;
    }
}
