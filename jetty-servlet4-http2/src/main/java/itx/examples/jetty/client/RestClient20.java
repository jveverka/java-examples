package itx.examples.jetty.client;

import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.SystemInfoService;
import org.eclipse.jetty.http.HttpFields;
import org.eclipse.jetty.http.HttpURI;
import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.http.MetaData;
import org.eclipse.jetty.http2.api.Session;
import org.eclipse.jetty.http2.api.server.ServerSessionListener;
import org.eclipse.jetty.http2.client.HTTP2Client;
import org.eclipse.jetty.http2.frames.HeadersFrame;
import org.eclipse.jetty.util.FuturePromise;
import org.eclipse.jetty.util.Jetty;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.security.KeyStore;
import java.util.concurrent.TimeUnit;

public class RestClient20 implements SystemInfoService, AutoCloseable {

    final private static Logger LOG = LoggerFactory.getLogger(RestClient20.class);
    final private static String USER_AGENT = "User-Agent";
    final private static String USER_AGENT_VERSION = "RestClient20" + "/" + Jetty.VERSION;

    private HTTP2Client client;
    private Session session;
    private HttpURI getSystemInfoURI;

    public RestClient20(String host, int port) {
        try {
            this.client = new HTTP2Client();
            this.getSystemInfoURI = new HttpURI("https://" + host + ":" + port + "/data/system/info");
            KeyStore keyStore = SystemUtils.loadJKSKeyStore("client.jks", "secret");
            SslContextFactory sslContextFactory = new SslContextFactory();
            sslContextFactory.setTrustStore(keyStore);
            sslContextFactory.setTrustStorePassword("secret");
            client.addBean(sslContextFactory);
            client.start();
            FuturePromise<Session> sessionPromise = new FuturePromise<>();
            client.connect(sslContextFactory, new InetSocketAddress(host, port), new ServerSessionListener.Adapter(), sessionPromise);
            session = sessionPromise.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }

    }

    @Override
    public SystemInfo getSystemInfo() {
        HttpFields requestFields = new HttpFields();
        requestFields.put(USER_AGENT, USER_AGENT_VERSION);
        MetaData.Request request = new MetaData.Request("GET", getSystemInfoURI, HttpVersion.HTTP_2, requestFields);
        HeadersFrame headersFrame = new HeadersFrame(request, null, true);
        GetListener getListener = new GetListener();
        session.newStream(headersFrame, new FuturePromise<>(), getListener);
        return getListener.get(SystemInfo.class);
    }

    @Override
    public void close() throws Exception {
        client.stop();
    }
}
