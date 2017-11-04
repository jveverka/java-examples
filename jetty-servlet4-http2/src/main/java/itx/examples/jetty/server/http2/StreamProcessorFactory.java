package itx.examples.jetty.server.http2;

import org.eclipse.jetty.http2.api.Stream;

public interface StreamProcessorFactory {

    Stream.Listener create();

}
