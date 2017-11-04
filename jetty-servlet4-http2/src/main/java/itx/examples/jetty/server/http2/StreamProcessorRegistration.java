package itx.examples.jetty.server.http2;

import org.eclipse.jetty.http2.api.Stream;

public class StreamProcessorRegistration {

    private String urn;
    private StreamProcessorFactory factory;

    public StreamProcessorRegistration(String urn, StreamProcessorFactory factory) {
        this.factory = factory;
        this.urn = urn;
    }

    public String getUrn() {
        return urn;
    }

    public Stream.Listener getListener() {
        return factory.create();
    }

}
