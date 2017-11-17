package itx.examples.jetty.server;

import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.server.service.SystemInfoService;
import itx.examples.jetty.server.service.SystemInfoServiceImpl;
import itx.examples.jetty.server.servlet.RestApplication;
import itx.examples.jetty.server.servlet.SystemInfoServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.jboss.resteasy.plugins.server.servlet.HttpServletDispatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    private Server server;

    public void runServer() {
        try {
            LOG.info("Jetty server starting ...");
            SystemInfoService systemInfoService = new SystemInfoServiceImpl();
            KeyStore keyStore = SystemUtils.loadJKSKeyStore("server.jks", "secret");
            ServerBuilder serverBuilder = new ServerBuilder();

            ServletHolder servletHolderDataSync = new ServletHolder(new SystemInfoServlet(systemInfoService));
            serverBuilder.addServletHolder("/system/info/*", servletHolderDataSync);

            ServletHolder restHolder = new ServletHolder(new HttpServletDispatcher());
            restHolder.setInitParameter("javax.ws.rs.Application", "itx.examples.jetty.server.servlet.RestApplication");
            serverBuilder.addServletHolder("/rest/*", restHolder);

            serverBuilder.setKeyStore(keyStore);
            serverBuilder.setKeystorePassword("secret");
            server = serverBuilder.build();
            server.start();
            LOG.info("Jetty server started.");
            Runtime.getRuntime().addShutdownHook(new Thread() {
                public void run() {
                    stopServer();
                }
            });
        } catch (Exception e) {
            LOG.error("ERROR: ", e);
        }
    }

    public void stopServer() {
        try {
            server.stop();
            LOG.info("stopped");
        } catch (Exception e) {
            LOG.error("ERROR: ", e);
        }
    }

    public static void main(String[] args) {
        Main main = new Main();
        main.runServer();
    }

}
