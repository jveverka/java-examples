package itx.examples.modules.application;

import itx.examples.modules.service01.Service01;
import itx.examples.modules.service01.Service01Impl;
import itx.examples.modules.service02.Service02;
import itx.examples.modules.service02.Service02Impl;
import itx.examples.modules.serviceclient.ServiceClient;
import itx.examples.modules.serviceregistry.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) throws InterruptedException {
        LOG.info("java modules example started {} ...", args.length);

        LOG.info("initializing application ...");
        ServiceRegistry serviceRegistry = ServiceRegistry.getInstance();
        Service01 service01 = new Service01Impl();
        Service02 service02 = new Service02Impl();
        ServiceClient serviceClient = new ServiceClient(service01, service02);
        serviceRegistry.registerService(Service01.class, service01);
        serviceRegistry.registerService(Service02.class, service02);
        serviceRegistry.registerService(ServiceClient.class, serviceClient);
        LOG.info("application initialization done.");

        ServiceClient serviceClientInstance = serviceRegistry.getService(ServiceClient.class);
        serviceClientInstance.executeServices();
        LOG.info("done.");
    }

}
