package itx.examples.dropwizard.server.services;

import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import itx.examples.dropwizard.server.dto.DataRequest;
import itx.examples.dropwizard.server.dto.DataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;

public class DataServiceImpl implements DataService {

    private static final Logger LOG = LoggerFactory.getLogger(DataServiceImpl.class);

    private ListeningExecutorService pool;

    public DataServiceImpl() {
        LOG.info("initializing");
        pool = MoreExecutors.listeningDecorator(Executors.newFixedThreadPool(2));
    }

    @Override
    public ListenableFuture<DataResponse> getData(DataRequest dataRequest) {
        LOG.info("getData: {}", dataRequest.getData());
        return pool.submit(new DataTask(dataRequest));
    }

}
