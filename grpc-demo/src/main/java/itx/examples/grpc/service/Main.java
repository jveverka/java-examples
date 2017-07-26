package itx.examples.grpc.service;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);
    final private static String SAY_HELLO_SCENARIO = "sayHello";

    @Parameter(names = {"--host" }, description = "Host name of the server, if specified, runs in client mode.")
    private String host;

    @Parameter(names = {"--port" }, description = "Port of the server or client.")
    private int port;

    @Parameter(names = {"--scenario" }, description = "Name of client's scenario to run.")
    private String scenarioName;

    @Parameter(names = {"--help", "-h", "--info", "-i" }, help = true)
    private boolean help;

    public static void main(String[] argv) {
        Main main = new Main();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(main)
                .build();
        jCommander.parse(argv);
        try {
            main.run(jCommander);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public void run(JCommander jCommander) throws Exception {
        if (help) {
            jCommander.usage();
            System.exit(1);
        }
        if (host == null) {
            LOG.info("Starting gRPC server at port {}", port);
            final SimpleServer server = new SimpleServer(port);
            server.start();
            server.blockUntilShutdown();
            LOG.info("gRPC server stopped.");
        } else {
            LOG.info("Starting gRPC client {}:{} with scenario {}", host, port, scenarioName);
            SimpleClient client = new SimpleClient(host, port);
            try {
                switch(scenarioName) {
                    case SAY_HELLO_SCENARIO:
                        HelloReply helloReply = client.greet("world");
                        LOG.info("response from server: {}", helloReply.getMessage());
                        break;
                    default:
                        LOG.error("Unsupported scenario name: {}", scenarioName);
                }
            } finally {
                client.shutdown();
                LOG.info("gRPC client stopped.");
            }
        }
    }

}
