package br.com.newsletter.poc;

import br.com.newsletter.poc.subscriber.service.AmazonQueue;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;


@Path("/hello")
public class GreetingResource {

    @Inject
    private AmazonQueue amazonQueue;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "Hello from Quarkus REST";
    }

    @GET
    @Path("/teste")
    @Produces(MediaType.TEXT_PLAIN)
    public String teste() {
        amazonQueue.Send(null);
        return "Hello from Quarkus REST";
    }
}
