package com.paymentchain.transaction.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import lombok.Data;

@Entity
@Data
public class Transaction {

 @Id
 @GeneratedValue(strategy = GenerationType.AUTO)
 private long id;

 private String reference;
 private String accountIban;
 private LocalDate date;
 private double amount;
 private double fee;
 private String description;

 private int statusCode;

 private String channel;

 @Transient
 private Status status;

 // sincroniza statusCode con el enum
 @PostLoad
 private void fillStatus() {
  this.status = Status.fromCode(this.statusCode);
 }

 @PrePersist
 @PreUpdate
 private void fillStatusCode() {
  this.statusCode = (this.status != null ? this.status.getCode() : 0);
 }

 // ENUM
 public enum Status {
  PENDIENTE(1),
  LIQUIDADA(2),
  RECHAZADA(3),
  CANCELADA(4);

  private final int code;

  Status(int code) {
   this.code = code;
  }

  public int getCode() {
   return code;
  }

  public static Status fromCode(int code) {
   return switch (code) {
    case 1 -> PENDIENTE;
    case 2 -> LIQUIDADA;
    case 3 -> RECHAZADA;
    case 4 -> CANCELADA;
    default -> throw new IllegalArgumentException("Código inválido: " + code);
   };
  }
}
}
