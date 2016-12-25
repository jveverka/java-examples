package itx.examples.java.jackson;

/**
 * Created by gergej on 25.12.2016.
 */
public class ResponseMessage extends Message {

    private String data;
    private Long responseId;

    public ResponseMessage() {
        this.setType("response");
    }

    public ResponseMessage(String data, Long responseId) {
        this.setType("response");
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
