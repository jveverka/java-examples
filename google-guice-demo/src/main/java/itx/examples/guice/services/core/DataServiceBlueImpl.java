package itx.examples.guice.services.core;

public class DataServiceBlueImpl implements DataService {

    private String init;

    public DataServiceBlueImpl(String init) {
        this.init = init;
    }

    @Override
    public String getData(String data) {
        return "blue=" + init + data;
    }

}
