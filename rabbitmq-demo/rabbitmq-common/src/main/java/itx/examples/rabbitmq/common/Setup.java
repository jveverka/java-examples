package itx.examples.rabbitmq.common;

public final class Setup {

    public static final String DEFAULT_SERVER_HOST = "localhost";

    public static final String CLIENT_RESPONSE_QUEUE = "client-response";
    public static final String CLIENT_REQUEST_QUEUE = "client-request";

    public static String resolveServerHost(String[] args) {
        if (args.length >= 1) {
            return args[0];
        }
        return DEFAULT_SERVER_HOST;
    }

}
