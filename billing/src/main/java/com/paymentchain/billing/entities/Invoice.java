/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.paymentchain.billing.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
@Schema(name = "Invoice", description = "Model represent an invoice in the billing system")
public class Invoice {
   @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
   private long id;
   @Schema(name = "customerId", requiredMode = Schema.RequiredMode.REQUIRED, example = "2", defaultValue = "1", description = "Customer ID associated with the invoice")
   private long customerId;
    @Schema(name = "number", requiredMode = Schema.RequiredMode.REQUIRED, example = "3", defaultValue = "8", description = "Number given on physical invoice")
   private String number;
   private String detail;
   private double amount;  
}
