package itx.examples.jetty.client;

import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.common.dto.ConversationId;
import itx.examples.jetty.common.dto.Message;
import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.MessageListener;
import itx.examples.jetty.common.services.MessageServiceAsync;
import itx.examples.jetty.common.services.SystemInfoService;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.Callback;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

public class RestClient20 implements MessageServiceAsync, SystemInfoService, AutoCloseable {

    final private static Logger LOG = LoggerFactory.getLogger(RestClient20.class);
    final private static String USER_AGENT = "User-Agent";
    final private static String USER_AGENT_VERSION = "RestClient20/" + Jetty.VERSION;

    private HTTP2Client client;
    private HttpURI getSystemInfoURI;
    private InetSocketAddress address;
    private SslContextFactory sslContextFactory;

    public RestClient20(String host, int port) {
        try {
            this.client = new HTTP2Client();
            this.address = new InetSocketAddress(host, port);
            this.getSystemInfoURI = new HttpURI("https://" + host + ":" + port + "/data/system/info");
            KeyStore keyStore = SystemUtils.loadJKSKeyStore("client.jks", "secret");
            sslContextFactory = new SslContextFactory();
            sslContextFactory.setTrustStore(keyStore);
            sslContextFactory.setTrustStorePassword("secret");
            client.addBean(sslContextFactory);
            client.start();
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    @Override
    public SystemInfo getSystemInfo() {
        try {
            FuturePromise<Session> sessionPromise = new FuturePromise<>();
            client.connect(sslContextFactory, address, new ServerSessionListener.Adapter(), sessionPromise);
            Session session = sessionPromise.get(5, TimeUnit.SECONDS);
            HttpFields requestFields = new HttpFields();
            requestFields.put(USER_AGENT, USER_AGENT_VERSION);
            MetaData.Request request = new MetaData.Request("GET", getSystemInfoURI, HttpVersion.HTTP_2, requestFields);
            HeadersFrame headersFrame = new HeadersFrame(request, null, true);
            GetListener getListener = new GetListener();
            session.newStream(headersFrame, new FuturePromise<>(), getListener);
            SystemInfo response = getListener.get(SystemInfo.class);
            session.close(0, null, new Callback() {});
            return response;
        } catch (Exception e) {
            throw new HttpAccessException(e);
        }
    }

    @Override
    public void close() throws Exception {
        client.stop();
    }

    @Override
    public Boolean registerListener(MessageListener listener) {
        return null;
    }

    @Override
    public Boolean unregisterListener(ConversationId conversationId) {
        return null;
    }

    @Override
    public Long publishMessage(Message message) {
        return null;
    }

    @Override
    public Optional<List<Message>> getMessages(String groupId) {
        return null;
    }

    @Override
    public List<String> getGroups() {
        return null;
    }

    @Override
    public Optional<Long> cleanMessages(String groupId) {
        return null;
    }

}
