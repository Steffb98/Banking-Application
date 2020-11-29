package io.swagger.service;

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

    @Autowired
    public TransactionService(TransactionRepository transactionRepository){
        this.transactionRepository = transactionRepository;
    }

    public void addTransaction(Transaction transaction) {
        transaction.setDate(OffsetDateTime.now());
        transactionRepository.save(transaction);
    }

    public Optional<Transaction> getTransactionById(Long id) {
    return transactionRepository.findById(id);
    }

    public List<Transaction> getAllTransactionsFromUser(Long userId) { return (List<Transaction>) transactionRepository.getAllByBy_IdOrderByDate(userId); }

    public List<Transaction> getAllTransactionsFromAccount(String accountId) { return (List<Transaction>) transactionRepository.getTransactionsByAccount(accountId); }
}
