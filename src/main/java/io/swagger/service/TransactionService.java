package io.swagger.service;

import io.swagger.exception.*;
import io.swagger.model.Account;
import io.swagger.model.Transaction;
import io.swagger.repository.TransactionRepository;
import org.springframework.stereotype.Service;
import org.threeten.bp.LocalDateTime;

import javax.transaction.Transactional;
import java.util.List;

import static io.swagger.model.Account.TypeofaccountEnum.SAVING;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;
    private final AccountService accountService;
    private final UserService userService;
    private final AuthorizationService authorizationService;

    public TransactionService(TransactionRepository transactionRepository, AccountService accountService, UserService userService, AuthorizationService authorizationService) {
        this.transactionRepository = transactionRepository;
        this.accountService = accountService;
        this.userService = userService;
        this.authorizationService = authorizationService;
    }

    @Transactional(rollbackOn = Exception.class)
    public void addTransaction(Transaction transaction) throws BadInputException, NotFoundException, LimitReachedException, NotAuthorizedException, ForbiddenException {

        Account sender = accountService.getAccountByIban(transaction.getSender());
        Account receiver = accountService.getAccountByIban(transaction.getReceiver());

        //this field is used for authorization and for getting all transactions for the receiving user
        transaction.setReceivinguser(receiver.getUserid());

        if (sender.getIban().equals(receiver.getIban())) {
            throw new BadInputException(400, "Can not transfer to the same account");
        }

        if (sender.getTransactionlimit().compareTo(transaction.getAmount()) <= 0) {
            throw new BadInputException(400, "Amount exceed the transactionlimit");
        }

        if (sender.getTypeofaccount() == SAVING) {
            if (!receiver.getIban().equals(accountService.getAccountFromUserIdWhereTypeOfAccountEquals(sender.getUserid(), Account.TypeofaccountEnum.DEPOSIT).getIban())) {
                throw new BadInputException(400, "SAVING account can not transfer to another person's account");
            }
        }

        if (receiver.getTypeofaccount() == SAVING) {
            if (!sender.getIban().equals(accountService.getAccountFromUserIdWhereTypeOfAccountEquals(receiver.getUserid(), Account.TypeofaccountEnum.DEPOSIT).getIban())) {
                throw new BadInputException(400, "SAVING account can not receive from another person's account");
            }
        }

        transaction.setDate(LocalDateTime.now());
        accountService.updateBalance(sender, transaction.getAmount(), AccountService.TypeOfTransactionEnum.SUBTRACT);
        accountService.updateBalance(receiver, transaction.getAmount(), AccountService.TypeOfTransactionEnum.ADD);
        transactionRepository.save(transaction);
    }

    public Transaction getTransactionById(Long id) throws NotFoundException, NotAuthorizedException {
        Transaction transaction = transactionRepository.findTransactionById(id);
        authorizationService.checkTransactionAuthorization(transaction);
        if (transaction == null) {
            throw new NotFoundException(404, "No transaction found with this id");
        }

        return transaction;
    }

    public List<Transaction> getAllTransactionsFromUser(Long userId) throws NotFoundException, NotAuthorizedException {

        // checks for valid userId
        userService.getUserById(userId);

        List<Transaction> transactions = transactionRepository.getAllByPerforminguserOrReceivinguserOrderByDate(userId, userId);

        if (transactions.isEmpty()) {
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }

    public List<Transaction> getAllTransactionsFromAccount(String accountId) throws NotFoundException, BadInputException, NotAuthorizedException, ForbiddenException {

        // checks for valid iban
        accountService.getAccountByIban(accountId);

        List<Transaction> transactions = transactionRepository.findTransactionByReceiverOrSenderOrderByDate(accountId, accountId);

        if (transactions.isEmpty()) {
            throw new NotFoundException(404, "There are no transactions found for this account");
        }

        return transactions;
    }
}
