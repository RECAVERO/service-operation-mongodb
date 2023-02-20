package com.nttdata.btask.interfaces;

import com.nttdata.domain.models.OperationDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;

public interface OperationService {
  Uni<OperationDto> addOperation(OperationDto operationDto);
  Multi<OperationDto> getListOperation(OperationDto operationDto);
}
