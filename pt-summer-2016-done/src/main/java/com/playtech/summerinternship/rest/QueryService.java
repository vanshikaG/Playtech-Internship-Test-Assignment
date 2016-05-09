package com.playtech.summerinternship.rest;

import com.playtech.summerinternship.config.PropertiesLoader;
import com.playtech.summerinternship.domain.AggregatedMetric;
import com.playtech.summerinternship.service.AggregationFileReaderService;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("/aggr/query")
public class QueryService {

    @Path("/")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AggregatedMetric> pushMessage(@QueryParam(value = "pattern") String pattern,
                                              @QueryParam(value = "start") String start,
                                              @QueryParam(value = "end") String end) {

        PropertiesLoader.load();

        List<AggregatedMetric> read =
                AggregationFileReaderService.read(pattern, start, end);

        return read;
    }
}
