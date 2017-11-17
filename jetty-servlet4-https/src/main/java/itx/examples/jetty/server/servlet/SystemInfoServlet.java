package itx.examples.jetty.server.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import itx.examples.jetty.common.SystemInfo;
import itx.examples.jetty.server.service.SystemInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SystemInfoServlet extends HttpServlet {

    final private static Logger LOG = LoggerFactory.getLogger(SystemInfoServlet.class);
    final private ObjectMapper mapper = new ObjectMapper();
    final private SystemInfoService systemInfoService;

    public SystemInfoServlet(SystemInfoService systemInfoService) {
        this.systemInfoService = systemInfoService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LOG.info("GET: {}", request.getRequestURI());
        SystemInfo systemInfo = systemInfoService.getSystemInfo();
        mapper.writeValue(response.getWriter(), systemInfo);
        response.setStatus(200);
    }
    
}
