package itx.examples.dropwizard.server.rest;

import itx.examples.dropwizard.server.dto.DataRequest;
import itx.examples.dropwizard.server.dto.DataResponse;
import itx.examples.dropwizard.server.services.DataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.concurrent.ExecutionException;

@Path("/dataservice")
@Produces(MediaType.APPLICATION_JSON)
public class DataServiceRest {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceRest.class);

    public DataService dataService;

    public DataServiceRest(DataService dataService) {
        this.dataService = dataService;
    }

    @POST
    @Path("data")
    public Response getData(DataRequest dataRequest) {
        try {
            LOG.info("getData: {}", dataRequest.getData());
            DataResponse dataResponse = dataService.getData(dataRequest).get();
            return Response.ok(dataResponse).build();
        } catch (ExecutionException | InterruptedException e) {
            return Response.serverError().build();
        }
    }

}
