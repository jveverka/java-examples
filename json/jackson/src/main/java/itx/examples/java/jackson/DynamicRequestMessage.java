package itx.examples.java.jackson;

/**
 * Created by gergej on 31.12.2016.
 */
public class DynamicRequestMessage extends Message {

    public static final String TYPE = "DynamicRequestMessage";

    private String data;

    public DynamicRequestMessage() {
        super(TYPE);
    }

    public DynamicRequestMessage(String data) {
        super(TYPE);
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

}
