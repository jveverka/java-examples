package itx.examples.jetty.server;

import itx.examples.jetty.server.http2.ServerConnectionFactory;
import itx.examples.jetty.server.http2.StreamProcessorFactory;
import itx.examples.jetty.server.http2.StreamProcessorRegistration;
import org.eclipse.jetty.alpn.ALPN;
import org.eclipse.jetty.alpn.server.ALPNServerConnectionFactory;
import org.eclipse.jetty.http2.HTTP2Cipher;
import org.eclipse.jetty.http2.server.HTTP2CServerConnectionFactory;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class ServerBuilder {

    private Map<String, ServletHolder> servletHandlers;
    private Map<String, StreamProcessorRegistration> streamProcessors;
    private int secureHttpPort = 8443;
    private int httpPort = 8080;
    private KeyStore keyStore;
    private String keyStorePassword;

    public ServerBuilder() {
        this.servletHandlers = new HashMap<>();
        this.streamProcessors = new HashMap<>();
    }

    public ServerBuilder addStreamProcessorFactory(String urn, StreamProcessorFactory factory) {
        streamProcessors.put(urn, new StreamProcessorRegistration(urn, factory));
        return this;
    }

    public ServerBuilder addServletHolder(String urn, ServletHolder servletHolder) {
        servletHandlers.put(urn, servletHolder);
        return this;
    }

    public ServerBuilder setSecureHttpPort(int secureHttpPort) {
        this.secureHttpPort = secureHttpPort;
        return this;
    }

    public ServerBuilder setHttpPort(int httpPort) {
        this.httpPort = httpPort;
        return this;
    }

    public ServerBuilder setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
        return this;
    }

    public ServerBuilder setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
        return this;
    }

    public Server build() throws Exception {
        Server server = new Server();

        ServletContextHandler context = new ServletContextHandler(server, "/", ServletContextHandler.SESSIONS);
        servletHandlers.forEach((uri, servletHolder) -> { context.addServlet(servletHolder, uri);});
        server.setHandler(context);

        // HTTP Configuration
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme("https");
        httpConfig.setSecurePort(secureHttpPort);
        httpConfig.setSendXPoweredBy(true);
        httpConfig.setSendServerVersion(true);

        // HTTP Connector
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        HTTP2CServerConnectionFactory http2CServerConnectionFactory = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector http = new ServerConnector(server, httpConnectionFactory, http2CServerConnectionFactory);
        http.setPort(httpPort);
        server.addConnector(http);

        // SSL Context Factory for HTTPS and HTTP/2
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustStore(keyStore);
        sslContextFactory.setTrustStorePassword(keyStorePassword);
        sslContextFactory.setKeyStore(keyStore);
        sslContextFactory.setKeyStorePassword(keyStorePassword);
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // HTTP/2 Connection Factory
        ServerConnectionFactory h2 = new ServerConnectionFactory(httpsConfig, streamProcessors);

        NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol(http.getDefaultProtocol());

        // SSL Connection Factory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

        // HTTP/2 Connector
        ServerConnector http2Connector =
                new ServerConnector(server, ssl, alpn, h2, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(secureHttpPort);
        server.addConnector(http2Connector);

        ALPN.debug=false;

        return server;
    }

}
