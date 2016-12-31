package itx.examples.java.jackson.test;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.java.jackson.*;
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
    public void testListSerializationAndDeserialization() throws IOException {
        RequestMessage requestMessage = new RequestMessage("requestData");
        ResponseMessage responseMessage = new ResponseMessage("responseData", 5L);
        List<Message> messages = new ArrayList<>();
        messages.add(requestMessage);
        messages.add(responseMessage);
        ObjectMapper mapperForSerialization = new ObjectMapper();
        String jsonData = mapperForSerialization.writeValueAsString(messages);
        Assert.assertNotNull(jsonData);
        logger.info(jsonData);

        ObjectMapper mapperForDeserialization = new ObjectMapper();
        Collection<Message> deserializedMessages = mapperForDeserialization.readValue(jsonData, new TypeReference<Collection<Message>>() {});
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

    @Test
    public void testOneSerializationAndDeserialization() throws IOException {
        ResponseMessage responseMessage = new ResponseMessage("responseData", 5L);
        ObjectMapper mapperForSerialization = new ObjectMapper();
        String jsonData = mapperForSerialization.writeValueAsString(responseMessage);

        ObjectMapper mapperFordeserialization = new ObjectMapper();
        Message message = mapperFordeserialization.readValue(jsonData, Message.class);
        Assert.assertNotNull(message);
        if ("ResponseMessage".equals(message.getType())) {
            ResponseMessage message2 = (ResponseMessage) message;
            Assert.assertNotNull(message);

            Assert.assertEquals(responseMessage.getType(), message2.getType());
            Assert.assertEquals(responseMessage.getData(), message2.getData());
            Assert.assertEquals(responseMessage.getResponseId(), message2.getResponseId());
        } else {
            Assert.fail();
        }
    }

    @Test
    public void testDynamicRegistrationInObjectMapper() throws IOException {
        DynamicRequestMessage dynamicRequestMessage = new DynamicRequestMessage("dynamicRequestData");
        DynamicResponseMessage dynamicResponseMessage = new DynamicResponseMessage("dynamicResponseData", 10L);
        ObjectMapper mapperForSerialization = new ObjectMapper();
        mapperForSerialization.registerSubtypes(DynamicRequestMessage.class, DynamicResponseMessage.class);
        List<Message> messages = new ArrayList<>();
        messages.add(dynamicRequestMessage);
        messages.add(dynamicResponseMessage);
        String jsonData = mapperForSerialization.writeValueAsString(messages);
        Assert.assertNotNull(jsonData);
        logger.info(jsonData);

        ObjectMapper mapperForDeserialization = new ObjectMapper();
        mapperForDeserialization.registerSubtypes(DynamicRequestMessage.class, DynamicResponseMessage.class);
        Collection<Message> deserializedMessages = mapperForDeserialization.readValue(jsonData, new TypeReference<Collection<Message>>() {});
        Assert.assertNotNull(deserializedMessages);
        Assert.assertTrue(deserializedMessages.size() == 2);
        Message[] messageArray = deserializedMessages.toArray(new Message[]{});
        DynamicRequestMessage message1 = (DynamicRequestMessage)messageArray[0];
        DynamicResponseMessage message2 = (DynamicResponseMessage)messageArray[1];

        Assert.assertEquals(dynamicRequestMessage.getType(), message1.getType());
        Assert.assertEquals(dynamicRequestMessage.getData(), message1.getData());

        Assert.assertEquals(dynamicResponseMessage.getType(), message2.getType());
        Assert.assertEquals(dynamicResponseMessage.getData(), message2.getData());
        Assert.assertEquals(dynamicResponseMessage.getResponseId(), message2.getResponseId());
    }


}
