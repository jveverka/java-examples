package itx.examples.jetty.server;

import org.eclipse.jetty.http.HttpVersion;
import org.eclipse.jetty.server.*;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;

public class ServerBuilder {

    final private static Logger LOG = LoggerFactory.getLogger(ServerBuilder.class);

    private static final String SERVLET_CONTEXT_PATH = "/";
    private static final String SECURE_SCHEME = "https";
    private String keystorePassword = "secret";
    private int httpPort = 8080;
    private int httpsPort = 8443;
    private KeyStore keyStore;

    private Map<String, ServletHolder> servletHandlers;

    public ServerBuilder() {
        servletHandlers = new HashMap<>();
    }

    public ServerBuilder setKeystorePassword(String keystorePassword) {
        this.keystorePassword = keystorePassword;
        return this;
    }

    public ServerBuilder setHttpPort(int httpPort) {
        this.httpPort = httpPort;
        return this;
    }

    public ServerBuilder setHttpsPort(int httpsPort) {
        this.httpsPort = httpsPort;
        return this;
    }

    public ServerBuilder setKeyStore(KeyStore keyStore) {
        this.keyStore = keyStore;
        return this;
    }

    public ServerBuilder addServletHolder(String uri, ServletHolder servletHolder) {
        servletHandlers.put(uri, servletHolder);
        return this;
    }

    public Server build() throws Exception {
        Server server = new Server();

        ServletContextHandler context = new ServletContextHandler(server, SERVLET_CONTEXT_PATH, ServletContextHandler.SESSIONS);
        context.addEventListener(new ContainerListener());
        context.addEventListener(new ServletContextListenerImpl());
        servletHandlers.forEach((uri, servletHolder) -> { context.addServlet(servletHolder, uri);});
        server.setHandler(context);
        LOG.info("servlets registered");

        // HTTP Configuration
        HttpConfiguration httpConfig = new HttpConfiguration();
        httpConfig.setSecureScheme(SECURE_SCHEME);
        httpConfig.setSecurePort(httpsPort);
        httpConfig.setSendXPoweredBy(true);
        httpConfig.setSendServerVersion(true);
        httpConfig.setOutputBufferSize(32768);
        httpConfig.setRequestHeaderSize(8192);
        httpConfig.setResponseHeaderSize(8192);
        LOG.info("http configuration created");

        // HTTP Connector
        HttpConnectionFactory httpConnectionFactory = new HttpConnectionFactory(httpConfig);
        ServerConnector http = new ServerConnector(server, httpConnectionFactory);
        http.setPort(httpPort);
        server.addConnector(http);
        LOG.info("http connection factory created");

        // SSL Context Factory for HTTPS
        SslContextFactory sslContextFactory = new SslContextFactory();
        sslContextFactory.setKeyStore(keyStore);
        sslContextFactory.setKeyManagerPassword(keystorePassword);
        sslContextFactory.setExcludeCipherSuites("SSL_RSA_WITH_DES_CBC_SHA",
                "SSL_DHE_RSA_WITH_DES_CBC_SHA", "SSL_DHE_DSS_WITH_DES_CBC_SHA",
                "SSL_RSA_EXPORT_WITH_RC4_40_MD5",
                "SSL_RSA_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DHE_RSA_EXPORT_WITH_DES40_CBC_SHA",
                "SSL_DHE_DSS_EXPORT_WITH_DES40_CBC_SHA");

        // SSL HTTP Configuration
        HttpConfiguration httpsConfig = new HttpConfiguration(httpConfig);
        httpsConfig.addCustomizer(new SecureRequestCustomizer());

        // SSL Connector
        ServerConnector sslConnector = new ServerConnector(server,
                new SslConnectionFactory(sslContextFactory,HttpVersion.HTTP_1_1.asString()),
                new HttpConnectionFactory(httpsConfig));
        sslConnector.setPort(httpsPort);
        server.addConnector(sslConnector);

        return server;
    }

}
