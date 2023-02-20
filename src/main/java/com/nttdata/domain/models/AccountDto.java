package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountDto {
  private Long id;
  private int idTypeAccount;
  private String numberAccount;
  private double amount;
  private String registrationDate;
  private int idTypeCustomer;
  private int idCustomer;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
