package io.swagger.repository;

import io.swagger.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends CrudRepository<Account, Long> {

    Account findAccountByIban(String accountId);

    List<Account> findAccountsByUserid(Long userId);

    Account findAccountByUseridAndTypeofaccountEquals(Long userId, Account.TypeofaccountEnum typeOfAccount);


}
