package com.paymentchain.transaction.controller;

import com.paymentchain.transaction.entities.Transaction;
import com.paymentchain.transaction.entities.Transaction.Status;
import com.paymentchain.transaction.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/transaction")
public class TransactionRestController {

    @Autowired
    TransactionRepository transactionRepository;
    private String accountIban;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody Transaction input) {

        // reglas de negocio
        if (input.getAmount() == 0) {
            return ResponseEntity.badRequest().body("El monto no puede ser cero");
        }

        if (input.getFee() > 0) {
            input.setAmount(input.getAmount() - input.getFee());
        }

        if (input.getDate().isAfter(LocalDate.now())) {
            input.setStatus(Status.PENDIENTE);
        } else {
            input.setStatus(Status.LIQUIDADA);
        }

        Transaction saved = transactionRepository.save(input);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable("id") long id, @RequestBody Transaction input) {
        return transactionRepository.findById(id)
                .map(existing -> {
                    existing.setReference(input.getReference());
                    existing.setAccountIban(input.getAccountIban());
                    existing.setDate(input.getDate());
                    existing.setAmount(input.getAmount());
                    existing.setFee(input.getFee());
                    existing.setDescription(input.getDescription());
                    existing.setChannel(input.getChannel());
                    existing.setStatus(Status.fromCode(input.getStatusCode()));
                    Transaction updated = transactionRepository.save(existing);
                    return ResponseEntity.ok(updated);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Transaction> list() {
        return transactionRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> get(@PathVariable(name = "id") long id) {
        return transactionRepository.findById(id).map(x -> ResponseEntity.ok(x)).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/customer/transactions")
    public List<Transaction> get(@RequestParam(name = "accountIban") String accountIban) {
        return transactionRepository.findByAccountIban(accountIban);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") long id) {
        return transactionRepository.findById(id)
                .map(existing -> {
                    transactionRepository.delete(existing);
                    return ResponseEntity.ok().build();
                })
                .orElse(ResponseEntity.notFound().build());
    }


    // para devolver saldo + lista de transacciones
    record TransactionSummary(List<Transaction> transactions, double balance) {}
}
