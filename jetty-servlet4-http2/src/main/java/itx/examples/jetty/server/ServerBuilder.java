package itx.examples.jetty.server;

import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.common.services.EchoService;
import itx.examples.jetty.server.http2.ServerConnectionFactory;
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
    private EchoService echoService;

    public ServerBuilder() {
        servletHandlers = new HashMap<>();
    }

    public ServerBuilder addServletHolder(String uri, ServletHolder servletHolder) {
        servletHandlers.put(uri, servletHolder);
        return this;
    }

    public ServerBuilder setEchoService(EchoService echoService) {
        this.echoService = echoService;
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
        httpConfig.setSecurePort(8443);
        httpConfig.setSendXPoweredBy(true);
        httpConfig.setSendServerVersion(true);

        // HTTP Connector
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        HTTP2CServerConnectionFactory http2CServerConnectionFactory = new HTTP2CServerConnectionFactory(httpConfig);
        ServerConnector http = new ServerConnector(server, httpConnectionFactory, http2CServerConnectionFactory);
        http.setPort(8080);
        server.addConnector(http);

        // SSL Context Factory for HTTPS and HTTP/2
        KeyStore keyStore = SystemUtils.loadJKSKeyStore("server.jks", "secret");
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setTrustStore(keyStore);
        sslContextFactory.setTrustStorePassword("secret");
        sslContextFactory.setKeyStore(keyStore);
        sslContextFactory.setKeyStorePassword("secret");
        sslContextFactory.setCipherComparator(HTTP2Cipher.COMPARATOR);

        // HTTPS Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // HTTP/2 Connection Factory
        //HTTP2ServerConnectionFactory h2 = new HTTP2ServerConnectionFactory(httpsConfig);
        ServerConnectionFactory h2 = new ServerConnectionFactory(httpsConfig, echoService);

        NegotiatingServerConnectionFactory.checkProtocolNegotiationAvailable();
        ALPNServerConnectionFactory alpn = new ALPNServerConnectionFactory();
        alpn.setDefaultProtocol(http.getDefaultProtocol());

        // SSL Connection Factory
        SslConnectionFactory ssl = new SslConnectionFactory(sslContextFactory, alpn.getProtocol());

        // HTTP/2 Connector
        ServerConnector http2Connector =
                new ServerConnector(server, ssl, alpn, h2, new HttpConnectionFactory(httpsConfig));
        http2Connector.setPort(8443);
        server.addConnector(http2Connector);

        ALPN.debug=false;

        return server;
    }

}
