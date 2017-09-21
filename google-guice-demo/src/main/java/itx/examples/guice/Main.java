package itx.examples.guice;

import com.google.inject.Guice;
import com.google.inject.Injector;
import itx.examples.guice.services.SecondaryService;
import itx.examples.guice.services.core.DataService;
import itx.examples.guice.services.core.DataServiceBlueImpl;
import itx.examples.guice.services.core.DataServiceRedImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        LOG.info("main ...");
        DataService redDataService = new DataServiceRedImpl("R");
        DataService blueDataService = new DataServiceBlueImpl("B");
        Injector injector = Guice.createInjector(new BasicModule(redDataService, blueDataService));
        SecondaryService instance = injector.getInstance(SecondaryService.class);
        String xxx = instance.getData("xxx");
        LOG.info("result: {}", xxx);
    }

}
