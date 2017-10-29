package itx.examples.jetty.server;

import itx.examples.jetty.server.servlet.SystemInfoServlet;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    private Server server;

    public void runServer() {
        try {
            LOG.info("Jetty server starting ...");
            ServerBuilder serverBuilder = new ServerBuilder();
            ServletHolder servletHolderDataSync = new ServletHolder(new SystemInfoServlet());
            serverBuilder.addServletHolder("/system/info/*", servletHolderDataSync);
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
