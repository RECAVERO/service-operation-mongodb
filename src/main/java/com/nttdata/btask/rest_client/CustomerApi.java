package com.nttdata.btask.rest_client;

import com.nttdata.domain.models.CustomerDto;
import io.smallrye.mutiny.Uni;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

@RegisterRestClient
@Path("/customers")
public interface CustomerApi {
  @POST
  @Path("/search")
  Uni<CustomerDto> findByNroDocument(CustomerDto customerDto);
}
