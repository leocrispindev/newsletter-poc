package br.com.newsletter.poc.subscriber.route;

import br.com.newsletter.poc.subscriber.controller.SubscriberController;
import br.com.newsletter.poc.subscriber.model.SubscriberDto;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("api")
public class SubscriberResource {

    @Inject
    private SubscriberController subscriberController;

    @POST
    @Path("/sub")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response Subscribe(SubscriberDto subscriberDto) {

        try {
            subscriberController.HandleSubscriber(subscriberDto);

            return Response.status(201).build();

        }catch (IllegalArgumentException e) {

            return Response.status(422).entity(e.getMessage()).build();

        }catch (Exception e) {
            return Response.status(500).entity("Error send subscription").build();
        }


    }

}
