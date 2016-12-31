package itx.examples.java.jackson;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Created by gergej on 25.12.2016.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RequestMessage.class, name = "RequestMessage"),
        @JsonSubTypes.Type(value = ResponseMessage.class, name = "ResponseMessage") })
public abstract class Message {

    private String type;

    public Message(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
