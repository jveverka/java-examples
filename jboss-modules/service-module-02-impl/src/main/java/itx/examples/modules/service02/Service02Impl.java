package itx.examples.modules.service02;

import itx.examples.modules.serviceregistry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Service02Impl implements Service02 {

    final private static Logger LOG = LoggerFactory.getLogger(Service02Impl.class);

    static {
        LOG.info("init ...");
        ServiceRegistry instance = ServiceRegistry.getInstance();
        instance.registerService(Service02.class, new Service02Impl());
    }

    @Override
    public String getData() {
        return "dataFromService02";
    }

}
