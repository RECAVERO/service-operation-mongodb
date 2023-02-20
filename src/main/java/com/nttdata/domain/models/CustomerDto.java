package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {
  private String name;
  private String lastName;
  private Long nroDocument;
  private int typeCustomer;
  private int typeDocument;
}
