package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {
  private Long id;
  private int idTypeTransfer;
  private String numberAccountOrigin;
  private String numberAccountDestination;
  private double amount;
  private String stateTransferAccountOrigin;
  private String stateTransferAccountDestination;
  private String registrationDate;
  private int idTypeCustomer;
  private int idCustomer;
  private String created_datetime;
  private String updated_datetime;
  private String active;
}
