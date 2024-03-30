package br.com.newsletter.poc.subscriber.route;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("api")
public class CheckResource {
    @GET
    @Path("/check")
    @Produces(MediaType.TEXT_PLAIN)
    public String CheckApi() {
        return "Ok";
    }
}
