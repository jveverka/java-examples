package itx.java.examples.akka.eventbus;

import akka.actor.UntypedActor;
import akka.event.Logging;
import akka.event.LoggingAdapter;

public class Subscriber extends UntypedActor {
	
	LoggingAdapter log = Logging.getLogger(getContext().system(), this);
	
	public Subscriber() {
	}

	@Override
	public void onReceive(Object message) throws Throwable {
		log.info(message.toString());
	}

}
