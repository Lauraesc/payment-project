package com.paymentchain.transaction.repository;

import com.paymentchain.transaction.entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    //List<Transaction> findByAccountIban(String accountIban);

    @Query("SELECT t FROM Transaction t WHERE t.accountIban = ?1")
    public List<Transaction> findByAccountIban(String ibanAccount);

}