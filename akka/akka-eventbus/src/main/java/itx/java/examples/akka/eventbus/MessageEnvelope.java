package itx.java.examples.akka.eventbus;

public class MessageEnvelope {

	public final String topic;
    public final Object payload;
 
    public MessageEnvelope(String topic, Object payload) {
      this.topic = topic;
      this.payload = payload;
    }
    
    @Override
    public String toString() {
    	return "MSG:" + topic + ":payload=" + payload.toString();
    }
    
}
