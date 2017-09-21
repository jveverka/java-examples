package itx.examples.guice.services;

import com.google.inject.Inject;

public class SecondaryServiceImpl implements SecondaryService {

    @Inject
    private PrimaryService primaryService;

    @Override
    public String getData(String data) {
        return "ss=" + primaryService.getRedData(data) + ":" + primaryService.getBlueData(data);
    }

}
