package itx.examples.modules.application;

import itx.examples.modules.serviceclient.ServiceClient;
import itx.examples.modules.serviceregistry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        LOG.info("java modules example started ...");
        ServiceClient serviceClient = ServiceRegistry.getInstance().getService(ServiceClient.class);
        serviceClient.executeServices();
        LOG.info("done.");
    }

}
