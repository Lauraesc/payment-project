package com.paymentchain.transaction.repository;

import com.paymentchain.transaction.entities.Transaction;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    List<Transaction> findByAccountIban(String accountIban);

}