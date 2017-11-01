package itx.examples.jetty.server.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.IOException;
import java.io.PrintWriter;

public class DataPushServlet extends HttpServlet {

    final private static Logger LOG = LoggerFactory.getLogger(DataPushServlet.class);

    private String baseURI;

    public DataPushServlet(String baseURI) {
        LOG.info("init");
        this.baseURI = baseURI;
    }

    @Override
    public void init(ServletConfig config) throws ServletException {
        LOG.info("init ...");
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PushBuilder pushBuilder = request.newPushBuilder();
        pushBuilder
                .path("/log4j.properties")
                .addHeader("content-type", "image/png")
                .push();

        try(PrintWriter respWriter = response.getWriter();){
            respWriter.write("<html>" +
                    "<img src='images/kodedu-logo.png'>" +
                    "</html>");
        }
    }

}
