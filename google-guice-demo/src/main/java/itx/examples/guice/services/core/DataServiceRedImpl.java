package itx.examples.guice.services.core;

public class DataServiceRedImpl implements DataService {

    private String init;

    public DataServiceRedImpl(String init) {
        this.init = init;
    }

    @Override
    public String getData(String data) {
        return "red=" + init + data;
    }

}
