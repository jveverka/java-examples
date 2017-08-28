package itx.examples.dropwizard.server;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import itx.examples.dropwizard.server.rest.DataServiceRest;
import itx.examples.dropwizard.server.services.DataService;
import itx.examples.dropwizard.server.services.DataServiceImpl;
import itx.examples.dropwizard.server.ws.WSCreator;
import itx.examples.dropwizard.server.ws.WSocketServlet;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainApplication extends Application<ApplicationConfiguration> {

    private static final Logger LOG = LoggerFactory.getLogger(MainApplication.class);

    public static void main(String[] args) {
        LOG.info("Application started");
        try {
            MainApplication mainApplication = new MainApplication();
            mainApplication.run(args);
        } catch (Exception e) {
            LOG.error("Application Error: ", e);
        }
    }

    @Override
    public String getName() {
        LOG.info("getName");
        return "simple-dropwizard-app";
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        LOG.info("initialize");
        bootstrap.addBundle(new AssetsBundle("/assets", "/web", "index.html"));
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        LOG.info("run");
        DataService dataService = new DataServiceImpl();
        DataServiceRest dataServiceRest = new DataServiceRest(dataService);
        environment.jersey().register(dataServiceRest);

        WSCreator wsCreator = new WSCreator(dataService);
        WSocketServlet wSocketServlet = new WSocketServlet(wsCreator);
        environment.getApplicationContext().getServletHandler().addServletWithMapping(
                new ServletHolder(wSocketServlet), "/ws/*"
        );
        LOG.info("done.");
    }

}
