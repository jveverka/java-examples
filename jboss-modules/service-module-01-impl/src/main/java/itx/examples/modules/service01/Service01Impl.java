package itx.examples.modules.service01;

import itx.examples.modules.serviceregistry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service01Impl implements Service01 {

    final private static Logger LOG = LoggerFactory.getLogger(Service01Impl.class);

    static {
        LOG.info("init ...");
        ServiceRegistry instance = ServiceRegistry.getInstance();
        instance.registerService(Service01.class, new Service01Impl());
    }

    @Override
    public String getData() {
        return "dataFromService01";
    }

}
