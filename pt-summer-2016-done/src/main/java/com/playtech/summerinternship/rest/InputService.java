package com.playtech.summerinternship.rest;

import com.playtech.summerinternship.config.PropertiesLoader;
import com.playtech.summerinternship.domain.Metric;
import com.playtech.summerinternship.internal.storage.InternalProcessor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.xml.ws.http.HTTPException;

/**
 * Endpoint which handles with receiving metric information for parties.
 * It contains a single POST method for publishing messages which afterwards should be processed accordingly.
 */
@Path("push")
public class InputService {

    /**
     * Handler for publishing a message. Message should be validated to respect the format an 400 Bad Request response
     * will be returned in case of wrong format.
     * @param message - message to be published.
     *        Format is: <i><metric path> <metric value> <timestamp></i>
              Metric path is a string. For example: „sitename.host.javaserver.brand.activity.login.reponse“.
              Metric value is a number
              Metric timestamp is timestamp in milliseconds.
              Example of full message: „local.random.diceroll 321 1461140237“.
     * @return
     */
    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String pushMessage(@QueryParam(value = "message") String message) {
        PropertiesLoader.load();

        Metric metric = null;
        try {
            metric = new Metric(message);
            InternalProcessor.aggregate(metric);
        } catch (IllegalArgumentException e1) {
            // TODO respond throw 400 bad request
            throw new HTTPException(400);
        }

        return "You have pushed: " + metric.toString();
    }


}
