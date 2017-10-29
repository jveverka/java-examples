package itx.examples.jetty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.jetty.common.SystemInfo;
import itx.examples.jetty.common.SystemUtils;
import org.eclipse.jetty.client.HttpClient;
import org.eclipse.jetty.util.ssl.SslContextFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.KeyStore;


public class RestClient {

    final private static Logger LOG = LoggerFactory.getLogger(RestClient.class);
    private static final String JKS_PASSWORD = "secret";
    private static final String KEYSTORE_PATH = "client.jks";

    private String url;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    public RestClient(String url) {
        this.url = url;
        this.mapper = new ObjectMapper();
        try {
            if (url.startsWith("https://")) {
                LOG.info("initializing HTTPS client");
                KeyStore keyStore = SystemUtils.loadJKSKeyStore(KEYSTORE_PATH, JKS_PASSWORD);
                SslContextFactory sslContextFactory = new SslContextFactory();
                sslContextFactory.setTrustStore(keyStore);
                sslContextFactory.setTrustStorePassword(JKS_PASSWORD);
                httpClient = new HttpClient(sslContextFactory);
            } else {
                LOG.info("initializing HTTP client");
                httpClient = new HttpClient();
            }
            httpClient.start();
        } catch (Exception e) {
            LOG.error("ERROR: ",e);
        }
    }

    public SystemInfo getSystemInfo() throws Exception {
        String contentAsString = httpClient.GET(url).getContentAsString();
        return mapper.readValue(contentAsString, SystemInfo.class);
    }

}
