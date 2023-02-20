package com.nttdata.domain.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
  private int typeCard;
  private String numberCard;
  private String numberAccountAssociated;
  private String pin;
  private String dueDate;
  private String codeValidation;
  private Long nroDocument;
  private int typeDocument;
}
