package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    Iterable<Transaction> getAllByPerforminguserOrderByDate(Long userId);

    @Query("select t from Transaction t where t.receiver = ?1 or t.sender = ?1")
    Iterable<Transaction> getTransactionsByAccount(String accountId);

    Transaction findTransactionById(Long id);

}
