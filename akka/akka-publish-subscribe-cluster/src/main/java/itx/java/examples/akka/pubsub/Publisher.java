package itx.java.examples.akka.pubsub;

import akka.actor.ActorRef;
import akka.actor.UntypedActor;
import akka.cluster.pubsub.DistributedPubSub;
import akka.cluster.pubsub.DistributedPubSubMediator;

public class Publisher extends UntypedActor {

	// activate the extension
	ActorRef mediator = DistributedPubSub.get(getContext().system()).mediator();

	public void onReceive(Object msg) {
		if (msg instanceof String) {
			String in = (String) msg;
			String out = in.toUpperCase();
			mediator.tell(new DistributedPubSubMediator.Publish("content", out), getSelf());
		} else {
			unhandled(msg);
		}
	}
}
