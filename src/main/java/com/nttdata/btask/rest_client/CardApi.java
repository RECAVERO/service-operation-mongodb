package com.nttdata.btask.rest_client;

import com.nttdata.domain.models.CardDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
@RegisterRestClient
@Path("/cards")
public interface CardApi {

  @POST
  @Path("/search")
  Uni<CardDto> getByCard(CardDto accountDto);
}
