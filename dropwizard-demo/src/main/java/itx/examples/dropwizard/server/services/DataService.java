package itx.examples.dropwizard.server.services;

import com.google.common.util.concurrent.ListenableFuture;
import itx.examples.dropwizard.server.dto.DataRequest;
import itx.examples.dropwizard.server.dto.DataResponse;

public interface DataService {

    ListenableFuture<DataResponse> getData(DataRequest dataRequest);

}
