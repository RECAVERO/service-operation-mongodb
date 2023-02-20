package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseUserDto {
  private CardDto cardDto;
  private CustomerDto customerDto;
  private String token;
  private String statusCustomer;
  private String statusCard;
}
