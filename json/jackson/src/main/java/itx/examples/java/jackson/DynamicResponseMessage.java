package itx.examples.java.jackson;

/**
 * Created by gergej on 31.12.2016.
 */
public class DynamicResponseMessage extends Message {

    public static final String TYPE = "DynamicResponseMessage";

    private String data;
    private Long responseId;

    public DynamicResponseMessage() {
        super(TYPE);
    }

    public DynamicResponseMessage(String data, Long responseId) {
        super(TYPE);
        this.data = data;
        this.responseId = responseId;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Long getResponseId() {
        return responseId;
    }

    public void setResponseId(Long responseId) {
        this.responseId = responseId;
    }

}
