package com.nttdata.infraestructure.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Operation {
  private int typeCard;
  private String numberCard;
  private String numberAccountAssociated;
  private String pin;
  private String dueDate;
  private String codeValidation;
  private Long nroDocument;
  private int typeDocument;
  private int typeOperation;
  private double amount;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
