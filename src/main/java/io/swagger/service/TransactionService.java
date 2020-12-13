package io.swagger.service;

import io.swagger.exception.BadInputException;
import io.swagger.exception.LimitReachedException;
import io.swagger.exception.NotFoundException;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Transactional(rollbackOn = Exception.class)
    public void addTransaction(Transaction transaction) throws BadInputException, NotFoundException, LimitReachedException {

        Account sender = accountService.getAccountByIban(transaction.getSender());
        Account receiver = accountService.getAccountByIban(transaction.getReceiver());

        if (sender.getIban().equals(receiver.getIban())){
            throw new BadInputException(401, "Can not transfer to the same account");
        }

        if (sender.getTransactionlimit().compareTo(transaction.getAmount()) <= 0){
            throw new BadInputException(401, "Amount exceed the transactionlimit");
        }

        if (sender.getTypeofaccount() == Account.TypeofaccountEnum.SAVING){
            if (!receiver.getIban().equals(accountService.getAccountFromUserIdWhereTypeOfAccountEquals(sender.getUserid(), Account.TypeofaccountEnum.DEPOSIT).getIban())){
                throw new BadInputException(401, "SAVING account can not transfer to another person's account");
            }
        }

        if (receiver.getTypeofaccount() == Account.TypeofaccountEnum.SAVING){
            if (!sender.getIban().equals(accountService.getAccountFromUserIdWhereTypeOfAccountEquals(receiver.getUserid(), Account.TypeofaccountEnum.DEPOSIT).getIban())){
                throw new BadInputException(401, "SAVING account can not receive from another person's account");
            }
        }

        transaction.setDate(OffsetDateTime.now());
        accountService.updateBalance(sender, transaction.getAmount(), AccountService.TypeOfTransactionEnum.SUBTRACT);
        accountService.updateBalance(receiver, transaction.getAmount(), AccountService.TypeOfTransactionEnum.ADD);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) throws NotFoundException {

        Transaction transaction = transactionRepository.findTransactionById(id);

        if ( transaction == null) {
            throw new NotFoundException(404, "No transaction found with this id");
        }

        return transaction;
    }

    public List<Transaction> getAllTransactionsFromUser(Long userId) throws NotFoundException {

        // checks for valid userId
        userService.getUserById(userId);

        List<Transaction> transactions = transactionRepository.getAllByPerforminguserOrderByDate(userId);

        if (transactions.isEmpty()){
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }

    public List<Transaction> getAllTransactionsFromAccount(String accountId) throws NotFoundException, BadInputException {

        // checks for valid iban
        accountService.getAccountByIban(accountId);

        List<Transaction> transactions = transactionRepository.findTransactionByReceiverOrSenderOrderByDate(accountId, accountId);

        if (transactions.isEmpty()){
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }
}
