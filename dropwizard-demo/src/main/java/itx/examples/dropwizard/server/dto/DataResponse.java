package itx.examples.dropwizard.server.dto;

public class DataResponse {

    private String data;

    public DataResponse()  {
    }

    public DataResponse(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
