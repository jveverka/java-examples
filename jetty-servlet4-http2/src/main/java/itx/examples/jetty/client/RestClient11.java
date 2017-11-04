package itx.examples.jetty.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.jetty.common.dto.Message;
import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.MessageServiceSync;
import itx.examples.jetty.common.services.SystemInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.eclipse.jetty.client.HttpClient;

import java.util.List;
import java.util.Optional;

public class RestClient11 implements MessageServiceSync, SystemInfoService {

    final private static Logger LOG = LoggerFactory.getLogger(RestClient11.class);

    private String url;
    private HttpClient httpClient;
    private ObjectMapper mapper;

    public RestClient11(String url) {
        try {
            this.url = url;
            this.mapper = new ObjectMapper();
            this.httpClient = new HttpClient();
            httpClient.start();
        } catch (Exception e) {
            LOG.error("RestClient init ERROR: ", e);
        }
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

    @Override
    public SystemInfo getSystemInfo() {
        try {
            String requestURL = url + "/data/system/info";
            LOG.info("getSystemInfo: {}", requestURL);
            return mapper.readValue(httpClient.GET(requestURL).getContent(), SystemInfo.class);
        } catch (Exception e) {
            throw new HttpAccessException(e);
        }
    }

}
