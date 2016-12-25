package itx.examples.java.jackson.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.java.jackson.Message;
import itx.examples.java.jackson.RequestMessage;
import itx.examples.java.jackson.ResponseMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by gergej on 25.12.2016.
 */
public class TestSerializationAndDeserialization {

    private static Logger logger = LoggerFactory.getLogger(TestSerializationAndDeserialization.class);

    @Test
    public void testSerializationAndDeserialization() throws IOException {
        RequestMessage requestMessage = new RequestMessage("requestData");
        ResponseMessage responseMessage = new ResponseMessage("responseData", 5L);
        List<Message> messages = new ArrayList<>();
        messages.add(requestMessage);
        messages.add(responseMessage);
        ObjectMapper mapperForSerialization = new ObjectMapper();
        String jsonData = mapperForSerialization.writeValueAsString(messages);
        Assert.assertNotNull(jsonData);
        logger.info(jsonData);

        ObjectMapper mapperFordeserialization = new ObjectMapper();
        Collection<Message> deserializedMessages = mapperFordeserialization.readValue(jsonData, new TypeReference<Collection<Message>>() {});
        Assert.assertNotNull(deserializedMessages);
        Assert.assertTrue(deserializedMessages.size() == 2);
        Message[] messageArray = deserializedMessages.toArray(new Message[]{});
        RequestMessage message1 = (RequestMessage)messageArray[0];
        ResponseMessage message2 = (ResponseMessage)messageArray[1];

        Assert.assertEquals(requestMessage.getType(), message1.getType());
        Assert.assertEquals(requestMessage.getData(), message1.getData());

        Assert.assertEquals(responseMessage.getType(), message2.getType());
        Assert.assertEquals(responseMessage.getData(), message2.getData());
        Assert.assertEquals(responseMessage.getResponseId(), message2.getResponseId());
    }

}
