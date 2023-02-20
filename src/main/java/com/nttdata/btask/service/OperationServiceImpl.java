package com.nttdata.btask.service;

import com.nttdata.btask.interfaces.OperationService;
import com.nttdata.domain.contract.OperationRepository;
import com.nttdata.domain.models.OperationDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class OperationServiceImpl implements OperationService {
  private final OperationRepository operationRepository;

  public OperationServiceImpl(OperationRepository operationRepository) {
    this.operationRepository = operationRepository;
  }

  @Override
  public Uni<OperationDto> addOperation(OperationDto operationDto) {
    return operationRepository.addOperation(operationDto);
  }

  @Override
  public Multi<OperationDto> getListOperation(OperationDto operationDto) {
    return operationRepository.getListOperation(operationDto);
  }
}
