package itx.examples.java.jackson;

/**
 * Created by gergej on 25.12.2016.
 */
public class RequestMessage extends Message {

    private String data;

    public RequestMessage() {
        this.setType("request");
    }

    public RequestMessage(String data) {
        this.setType("request");
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
