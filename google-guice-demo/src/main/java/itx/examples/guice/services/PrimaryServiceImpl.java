package itx.examples.guice.services;

import com.google.inject.Inject;
import com.google.inject.name.Named;
import itx.examples.guice.services.core.DataService;

public class PrimaryServiceImpl implements PrimaryService {

    @Inject
    @Named("red")
    private DataService redDataService;

    @Inject
    @Named("blue")
    private DataService blueDataService;

    @Override
    public String getRedData(String data) {
        return "ps=" + redDataService.getData(data);
    }

    @Override
    public String getBlueData(String data) {
        return "ps=" + blueDataService.getData(data);
    }

}
