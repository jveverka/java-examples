package itx.examples.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import itx.examples.guice.services.PrimaryService;
import itx.examples.guice.services.PrimaryServiceImpl;
import itx.examples.guice.services.SecondaryService;
import itx.examples.guice.services.SecondaryServiceImpl;
import itx.examples.guice.services.core.DataService;

public class BasicModule extends AbstractModule {

    private DataService redDataService;
    private DataService blueDataService;

    public BasicModule(DataService redDataService, DataService blueDataService) {
        this.redDataService = redDataService;
        this.blueDataService = blueDataService;
    }

    @Override
    protected void configure() {
        bind(DataService.class).annotatedWith(Names.named("red")).toInstance(redDataService);
        bind(DataService.class).annotatedWith(Names.named("blue")).toInstance(blueDataService);
        bind(PrimaryService.class).to(PrimaryServiceImpl.class);
        bind(SecondaryService.class).to(SecondaryServiceImpl.class);
    }

}
