package itx.examples.jetty.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.common.dto.EchoMessage;
import itx.examples.jetty.common.services.EchoService;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.Stream;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.DataFrame;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class RestEchoClient20 implements EchoService, AutoCloseable {

    final private static Logger LOG = LoggerFactory.getLogger(RestEchoClient20.class);
    final private static String USER_AGENT = "User-Agent";
    final private static String USER_AGENT_VERSION = "RestClient20/" + Jetty.VERSION;

    private HTTP2Client client;
    private HttpURI echoServiceURI;
    private ObjectMapper objectMapper;
    private InetSocketAddress address;
    private SslContextFactory sslContextFactory;
    private Session session;

    public RestEchoClient20(String host, int port) {
        try {
            this.client = new HTTP2Client();
            this.objectMapper = new ObjectMapper();
            this.address = new InetSocketAddress(host, port);
            this.echoServiceURI = new HttpURI("https://" + host + ":" + port + "/stream/echo");
            KeyStore keyStore = SystemUtils.loadJKSKeyStore("client.jks", "secret");
            sslContextFactory = new SslContextFactory();
            sslContextFactory.setTrustStore(keyStore);
            sslContextFactory.setTrustStorePassword("secret");
            client.addBean(sslContextFactory);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public void connect() throws Exception {
        client.start();
        FuturePromise<Session> sessionPromise = new FuturePromise<>();
        client.connect(sslContextFactory, address, new ServerSessionListener.Adapter(), sessionPromise);
        session = sessionPromise.get(5, TimeUnit.SECONDS);
    }

    @Override
    public String ping(String message) {
        try {
            GetListener getListener = new GetListener();
            Stream stream = createStream(getListener);
            return sendMessage(getListener, stream, message, true);
        } catch (Exception e) {
            throw new HttpAccessException(e);
        }
    }

    public List<String> ping(List<String> messages) {
        try {
            List<String> responses = new ArrayList<>();
            GetListener getListener = new GetListener();
            Stream stream = createStream(getListener);
            for (int i=0; i<messages.size(); i++) {
                boolean endStream = (i == (messages.size() - 1));
                String response = sendMessage(getListener, stream, messages.get(i), endStream);
                responses.add(response);
                getListener.restart();
            }
            return responses;
        } catch (Exception e) {
            throw new HttpAccessException(e);
        }
    }

    @Override
    public void close() throws Exception {
        session.close(0, null, new Callback() {});
    }

    private Stream createStream(Stream.Listener listener) throws Exception {
        HttpFields requestFields = new HttpFields();
        requestFields.put(USER_AGENT, USER_AGENT_VERSION);
        MetaData.Request request = new MetaData.Request("GET", echoServiceURI, HttpVersion.HTTP_2, requestFields);
        HeadersFrame headersFrame = new HeadersFrame(request, null, false);
        FuturePromise<Stream> streamPromise = new FuturePromise<>();
        session.newStream(headersFrame, streamPromise, listener);
        return streamPromise.get();
    }

    private String sendMessage(GetListener getListener, Stream stream, String message, boolean endStream) throws JsonProcessingException {
        EchoMessage echoMessage = new EchoMessage(message);
        DataFrame dataFrame = new DataFrame(stream.getId(),
                ByteBuffer.wrap(objectMapper.writeValueAsBytes(echoMessage)), endStream);
        stream.data(dataFrame , new Callback() {});
        EchoMessage echoResponse = getListener.get(EchoMessage.class);
        getListener.restart();
        return echoResponse.getMessage();
    }

}
