package itx.examples.modules.serviceregistry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ServiceRegistry {

    final private static Logger LOG = LoggerFactory.getLogger(ServiceRegistry.class);
    final private static ServiceRegistry serviceRegistry = new ServiceRegistry();

    private Map<Class, Object> services;

    private ServiceRegistry() {
        LOG.info("init ...");
        services = new ConcurrentHashMap<>();
    }

    public void registerService(Class clazz, Object instance) {
        LOG.info("registerService: {}", clazz.getCanonicalName());
        services.put(clazz, instance);
    }

    public <T> T getService(Class<T> clazz) {
        LOG.info("getService: {}", clazz.getCanonicalName());
        return (T)services.get(clazz);
    }

    public void unRegisterService(Class clazz) {
        LOG.info("unRegisterService: {}", clazz.getCanonicalName());
        services.remove(clazz);
    }

    public static ServiceRegistry getInstance() {
        return serviceRegistry;
    }

}
