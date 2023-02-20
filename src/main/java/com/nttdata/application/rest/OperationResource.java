package com.nttdata.application.rest;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.nttdata.btask.interfaces.OperationService;
import com.nttdata.btask.rest_client.AccountApi;
import com.nttdata.btask.rest_client.CardApi;
import com.nttdata.btask.rest_client.CustomerApi;
import com.nttdata.domain.models.*;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Path("/operations")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OperationResource {

  @RestClient
  AccountApi accountApi;
  @RestClient
  CardApi cardApi;

  @RestClient
  CustomerApi customerApi;

  private final OperationService operationService;

  public OperationResource(OperationService operationService) {
    this.operationService = operationService;
  }

  @POST
  @Path("/motion/byNumberAccount")
  public Multi<OperationDto> getListOperation(OperationDto operationDto){
    System.out.println(operationDto);
    return operationService.getListOperation(operationDto);
  }

  @POST
  @Path("/account/deposit")
  public Uni<AccountDto> registerDeposit(AccountDto accountDto) {
    return accountApi.updateAccountAmountDeposit(accountDto).call(doc->{
      OperationDto operationDto = new OperationDto();
      operationDto.setNumberAccountAssociated(doc.getNumberAccount());
      operationDto.setTypeOperation(1);
      operationDto.setAmount(accountDto.getAmount());
      return operationService.addOperation(operationDto);
    });
  }

  @POST
  @Path("/account/withdrawal")
  public Uni<AccountDto> registerWithdrawal(AccountDto accountDto) {
    return accountApi.updateAccountAmountWithdrawal(accountDto).call(doc->{
      OperationDto operationDto = new OperationDto();
      operationDto.setNumberAccountAssociated(doc.getNumberAccount());
      operationDto.setTypeOperation(2);
      operationDto.setAmount(-1*accountDto.getAmount());
      return operationService.addOperation(operationDto);
    });
  }
  @POST
  @Path("/account/transfer")
  public Uni<TransferDto> registerTransfer(TransferDto transferDto) {
    return accountApi.registrarTransfer(transferDto).call(doc->{
      OperationDto operationDto = new OperationDto();
      operationDto.setNumberAccountAssociated(doc.getNumberAccountOrigin());
      operationDto.setTypeOperation(3);
      operationDto.setAmount(-1*transferDto.getAmount());
      return operationService.addOperation(operationDto);
    });
  }

  @POST
  public Uni<OperationDto> register(OperationDto operationDto) {
    return operationService.addOperation(operationDto);
  }

  @POST
  @Path("/user/card")
  public Uni<ResponseUserDto> getByUser(UserDto userDto) {
    ResponseUserDto responseDto = new ResponseUserDto();
    CustomerDto customerDto = new CustomerDto();
    CardDto cardDto = new CardDto();
    customerDto.setNroDocument(userDto.getNroDocument());
    customerDto.setTypeDocument(userDto.getTypeDocument());
    cardDto.setNumberCard(userDto.getNumberCard());
    cardDto.setCodeValidation(userDto.getCodeValidation());
    cardDto.setDueDate(userDto.getDueDate());

    return customerApi.findByNroDocument(customerDto).map(doc -> {
      CustomerDto customer = new CustomerDto();
      customer.setName(doc.getName());
      customer.setLastName(doc.getLastName());
      customer.setTypeCustomer(doc.getTypeCustomer());
      customer.setNroDocument(doc.getNroDocument());
      customer.setTypeDocument(doc.getTypeDocument());
      responseDto.setCustomerDto(customer);
      if(userDto.getNroDocument().equals(doc.getNroDocument())){
        responseDto.setStatusCustomer("OK");
      }
      return responseDto;
    }).call(c->{

      return  cardApi.getByCard(cardDto).map(doc->{
        CardDto card = new CardDto();
        System.out.println(doc);
        card.setTypeCard(doc.getTypeCard());
        card.setNumberAccountAssociated(doc.getNumberAccountAssociated());
        card.setNumberCard(doc.getNumberCard());
        card.setCodeValidation(doc.getCodeValidation());
        card.setPin(doc.getPin());
        card.setDueDate(doc.getDueDate());
        responseDto.setCardDto(card);
        if(userDto.getNumberCard().equals(doc.getNumberCard())){
          responseDto.setStatusCard("OK");
        }
        return responseDto;
      });
    }).map(ddd->{
      OperationDto operationDto = new OperationDto();
      if(responseDto.getStatusCustomer().equals("OK") && responseDto.getStatusCard().equals("OK")){
        operationDto.setNumberCard(userDto.getNumberCard());
        operationDto.setNroDocument(userDto.getNroDocument());
        operationDto.setCodeValidation(userDto.getCodeValidation());
        operationDto.setDueDate(userDto.getDueDate());
        operationDto.setPin(userDto.getPin());
        operationDto.setTypeCard(userDto.getTypeCard());
        operationDto.setTypeDocument(userDto.getTypeDocument());
        operationDto.setNumberAccountAssociated(userDto.getNumberAccountAssociated());
        operationDto.setCreated_datetime(this.getDateNow());
        operationDto.setUpdated_datetime(this.getDateNow());
        operationDto.setActive("S");
        responseDto.setToken(UUID.randomUUID().toString());
      }
      return responseDto;

    }).call(doc->{
      OperationDto operationDto = new OperationDto();
      if(responseDto.getStatusCustomer().equals("OK") && responseDto.getStatusCard().equals("OK")){
        operationDto.setNumberCard(userDto.getNumberCard());
        operationDto.setNroDocument(userDto.getNroDocument());
        operationDto.setCodeValidation(userDto.getCodeValidation());
        operationDto.setDueDate(userDto.getDueDate());
        operationDto.setPin(doc.getCardDto().getPin());
        operationDto.setTypeCard(userDto.getTypeCard());
        operationDto.setTypeDocument(userDto.getTypeDocument());
        operationDto.setNumberAccountAssociated(doc.getCardDto().getNumberAccountAssociated());
        operationDto.setCreated_datetime(this.getDateNow());
        operationDto.setUpdated_datetime(this.getDateNow());
        operationDto.setActive("S");
        responseDto.setToken(UUID.randomUUID().toString());
      }
      return operationService.addOperation(operationDto);
    });
  }

  @POST
  @Path("/card/search")
  public Uni<CardDto> getByCard(CardDto cardDto) {
    return cardApi.getByCard(cardDto);
  }

  @POST
  @Path("/customer/search")
  public Uni<CustomerDto> getByCustomer(CustomerDto customerDto) {
    return customerApi.findByNroDocument(customerDto);
  }

  @POST
  @Path("/account/byCustomer")
  public Uni<List<AccountDto>> getAccountByCustomer(AccountDto accountDto) {
    return accountApi.getAccountByCustomer(accountDto);
  }

  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }

  public static ResponseUserDto mapToDto(Object OperationDto) {
    return new ObjectMapper().convertValue(OperationDto, ResponseUserDto.class);
  }

}
