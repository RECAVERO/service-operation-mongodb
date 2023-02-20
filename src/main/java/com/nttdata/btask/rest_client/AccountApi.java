package com.nttdata.btask.rest_client;

import com.nttdata.domain.models.AccountDto;
import com.nttdata.domain.models.TransferDto;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.List;

@RegisterRestClient
@Path("/accounts")
public interface AccountApi {
  @POST
  @Path("/deposit")
  Uni<AccountDto> updateAccountAmountDeposit(AccountDto accountDto);

  @POST
  @Path("/withdrawal")
  Uni<AccountDto> updateAccountAmountWithdrawal(AccountDto accountDto);

  @POST
  @Path("/transfer")
  Uni<TransferDto> registrarTransfer(TransferDto transferDto);

  @POST
  @Path("/byCustomer")
  Uni<List<AccountDto>> getAccountByCustomer(AccountDto accountDto);
}
