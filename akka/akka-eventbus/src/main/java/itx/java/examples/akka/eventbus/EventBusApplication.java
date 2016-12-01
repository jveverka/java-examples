package itx.java.examples.akka.eventbus;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;

public class EventBusApplication {

	public static void main(String[] args) throws InterruptedException {
		
		final ActorSystem actorSystem = ActorSystem.create("ServerEvents");
		final ActorRef actor1 = actorSystem.actorOf(Props.create(Subscriber.class), "subscriber1");
		final ActorRef actor2 = actorSystem.actorOf(Props.create(Subscriber.class), "subscriber2");
		final ActorRef actor3 = actorSystem.actorOf(Props.create(Subscriber.class), "subscriber3");
		
		LookupBusImpl lookupBus = new LookupBusImpl();
		lookupBus.subscribe(actor1, "greetings");
		lookupBus.subscribe(actor2, "greetings");
		lookupBus.subscribe(actor3, "greetings");
		lookupBus.subscribe(actor2, "time");
		lookupBus.subscribe(actor3, "time");
		
		Thread.sleep(2000);
		
		lookupBus.publish(new MessageEnvelope("time", "time=" + System.currentTimeMillis()));
		lookupBus.publish(new MessageEnvelope("greetings", "hello1"));
		
	}

}
