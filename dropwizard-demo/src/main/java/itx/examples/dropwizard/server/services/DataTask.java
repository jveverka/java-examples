package itx.examples.dropwizard.server.services;

import itx.examples.dropwizard.server.dto.DataRequest;
import itx.examples.dropwizard.server.dto.DataResponse;

import java.util.concurrent.Callable;

public class DataTask implements Callable<DataResponse> {

    private DataRequest request;

    public DataTask(DataRequest request) {
        this.request = request;
    }

    @Override
    public DataResponse call() throws Exception {
        return new DataResponse(request.getData());
    }

}
