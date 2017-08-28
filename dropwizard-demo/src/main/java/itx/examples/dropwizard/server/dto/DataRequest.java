package itx.examples.dropwizard.server.dto;

public class DataRequest {

    private String data;

    public DataRequest() {
    }

    public DataRequest(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
