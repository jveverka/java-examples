package itx.examples.modules.serviceclient;

import itx.examples.modules.service01.Service01;
import itx.examples.modules.service02.Service02;
import itx.examples.modules.serviceregistry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServiceClient {

    final private static Logger LOG = LoggerFactory.getLogger(ServiceClient.class);

    static {
        LOG.info("init ...");
        ServiceRegistry instance = ServiceRegistry.getInstance();
        Service01 service01 = instance.getService(Service01.class);
        Service02 service02 = instance.getService(Service02.class);
        ServiceClient serviceClient = new ServiceClient(service01, service02);
        instance.registerService(ServiceClient.class, serviceClient);
    }

    private Service01 service01;
    private Service02 service02;

    private ServiceClient(Service01 service01, Service02 service02) {
        this.service01 = service01;
        this.service02 = service02;
    }

    public void executeServices() {
        LOG.info("fromService01: {}", service01.getData());
        LOG.info("fromService02: {}", service02.getData());
    }

}
