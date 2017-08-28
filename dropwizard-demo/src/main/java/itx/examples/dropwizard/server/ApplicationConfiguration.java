package itx.examples.dropwizard.server;

import io.dropwizard.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApplicationConfiguration extends Configuration {

    private static final Logger LOG = LoggerFactory.getLogger(ApplicationConfiguration.class);

    public ApplicationConfiguration() {
        LOG.info("config init ...");
    }

}
