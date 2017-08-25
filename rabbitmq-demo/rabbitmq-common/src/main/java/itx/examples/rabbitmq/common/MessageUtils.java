package itx.examples.rabbitmq.common;

import java.io.*;

public final class MessageUtils {

    public static byte[] getBytes(MessageData messageData) throws IOException {
        ByteArrayOutputStream bas = new ByteArrayOutputStream();
        DataOutputStream dos = new DataOutputStream(bas);
        dos.writeLong(messageData.getContextId());
        dos.writeUTF(messageData.getMessage());
        dos.flush();
        dos.close();
        return bas.toByteArray();
    }

    public static MessageData fromByteArray(byte[] data) throws IOException {
        MessageData messageData = new MessageData();
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        DataInputStream dis = new DataInputStream(bis);
        messageData.setContextId(dis.readLong());
        messageData.setMessage(dis.readUTF());
        return messageData;
    }

}
