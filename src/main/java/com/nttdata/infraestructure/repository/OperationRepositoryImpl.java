package com.nttdata.infraestructure.repository;

import com.nttdata.domain.contract.OperationRepository;
import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.OperationDto;
import io.quarkus.mongodb.reactive.ReactiveMongoClient;
import io.quarkus.mongodb.reactive.ReactiveMongoCollection;
import io.quarkus.mongodb.reactive.ReactiveMongoDatabase;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.bson.Document;

import javax.enterprise.context.ApplicationScoped;
import java.text.SimpleDateFormat;
import java.util.Date;

@ApplicationScoped
public class OperationRepositoryImpl implements OperationRepository {
  private final ReactiveMongoClient reactiveMongoClient;

  public OperationRepositoryImpl(ReactiveMongoClient reactiveMongoClient) {
    this.reactiveMongoClient = reactiveMongoClient;
  }

  @Override
  public Uni<OperationDto> addOperation(OperationDto operationDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("operations");
    ReactiveMongoCollection<Document> collection = database.getCollection("operation");

    Document document = new Document()
        .append("typeCard", operationDto.getTypeCard())
        .append("numberCard", operationDto.getNumberCard())
        .append("numberAccountAssociated", operationDto.getNumberAccountAssociated())
        .append("pin", operationDto.getPin())
        .append("dueDate", operationDto.getDueDate())
        .append("codeValidation", operationDto.getCodeValidation())
        .append("nroDocument", operationDto.getNroDocument())

        .append("typeOperation", operationDto.getTypeOperation())
        .append("typeDocument", operationDto.getTypeDocument())
        .append("amount", operationDto.getAmount())
        .append("created_datetime", this.getDateNow())
        .append("updated_datetime", this.getDateNow())
        .append("active", "S");

    System.out.println("OK_OK");

    return collection.insertOne(document).replaceWith(operationDto);
  }

  @Override
  public Multi<OperationDto> getListOperation(OperationDto operationDto) {
    ReactiveMongoDatabase database = reactiveMongoClient.getDatabase("operations");
    ReactiveMongoCollection<Document> collection = database.getCollection("operation");


    return collection
        .find(new Document("numberAccountAssociated", operationDto.getNumberAccountAssociated())).map(doc->{
          OperationDto operationDto1 = new OperationDto();
          operationDto1.setTypeOperation(doc.getInteger("typeOperation"));
          operationDto1.setTypeOperation(doc.getInteger("typeCard"));
          operationDto1.setTypeOperation(doc.getInteger("numberCard"));
          operationDto1.setTypeOperation(doc.getInteger("pin"));
          operationDto1.setTypeOperation(doc.getInteger("dueDate"));
          operationDto1.setTypeOperation(doc.getInteger("codeValidation"));
          operationDto1.setTypeOperation(doc.getInteger("nroDocument"));
          return operationDto1;
        }).filter(s->s.getActive().equals("S"));
  }


  private static String getDateNow(){
    Date date = new Date();
    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    return formatter.format(date).toString();
  }
}
