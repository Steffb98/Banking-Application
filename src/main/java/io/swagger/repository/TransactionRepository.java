package io.swagger.repository;

import io.swagger.model.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Long> {

    List<Transaction> getAllByPerforminguserOrderByDate(Long userId);

    Transaction findTransactionById(Long id);

    List<Transaction> findTransactionByReceiverOrSenderOrderByDate(String receiver, String sender);

}
