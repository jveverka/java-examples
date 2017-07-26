package itx.examples.grpc.service;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import itx.examples.grpc.service.commands.RepeatHelloScenario;
import itx.examples.grpc.service.commands.SayHelloScenario;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);
    final private static String SAY_HELLO_SCENARIO = "sayHello";
    final private static String REPEAT_HELLO_SCENARIO = "repeatHello";

    @Parameter(names = {"--host" }, description = "Host name of the server, if specified, runs in client mode.")
    private String host;

    @Parameter(names = {"--port" }, required = true, description = "Port of the server or client.")
    private int port;

    @Parameter(names = {"--help", "-h", "--info", "-i" }, help = true)
    private boolean help;

    public static void main(String[] argv) {
        Main main = new Main();
        SayHelloScenario sayHelloScenario = new SayHelloScenario();
        RepeatHelloScenario repeatHelloScenario = new RepeatHelloScenario();
        JCommander jCommander = JCommander.newBuilder()
                .addObject(main)
                .addCommand(SAY_HELLO_SCENARIO, sayHelloScenario)
                .addCommand(REPEAT_HELLO_SCENARIO, repeatHelloScenario)
                .build();
        jCommander.parse(argv);
        try {
            main.run(jCommander, sayHelloScenario, repeatHelloScenario);
        } catch (Exception e) {
            LOG.error("Exception: ", e);
        }
    }

    public void run(JCommander jCommander,
                    SayHelloScenario sayHelloScenario,
                    RepeatHelloScenario repeatHelloScenario) throws Exception {
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
            String scenarioName = jCommander.getParsedCommand();
            LOG.info("Starting gRPC client {}:{} with scenario {}", host, port, scenarioName);
            SimpleClient client = new SimpleClient(host, port);
            try {
                switch(scenarioName) {
                    case SAY_HELLO_SCENARIO:
                        HelloReply helloReply = client.greet(sayHelloScenario.getMessage());
                        LOG.info("response from server: {}", helloReply.getMessage());
                        break;
                    case REPEAT_HELLO_SCENARIO:
                        LOG.info("warming up, sending {} \"{}\" messages ", repeatHelloScenario.getWarmupCount(), repeatHelloScenario.getMessage());
                        sendHelloMessages(repeatHelloScenario.getWarmupCount(), repeatHelloScenario.getMessage(), client);
                        Thread.sleep(1000);
                        LOG.info("sending {} \"{}\" messages", repeatHelloScenario.getHelloCount(), repeatHelloScenario.getMessage());
                        long delay = System.currentTimeMillis();
                        sendHelloMessages(repeatHelloScenario.getHelloCount(), repeatHelloScenario.getMessage(), client);
                        delay = System.currentTimeMillis() - delay;
                        LOG.info("send {} messages in {}ms = {} msg/s", repeatHelloScenario.getHelloCount(), delay,
                                (repeatHelloScenario.getHelloCount()/(delay/1000f)));
                        break;
                    default:
                        LOG.error("Unsupported scenario name: {}", scenarioName);
                        break;
                }
            } finally {
                client.shutdown();
                LOG.info("gRPC client stopped.");
            }
        }
    }

    private void sendHelloMessages(int messageCount, String message, SimpleClient client) {
        for (int i=0; i<messageCount; i++) {
            client.greet(message);
        }
    }

}
