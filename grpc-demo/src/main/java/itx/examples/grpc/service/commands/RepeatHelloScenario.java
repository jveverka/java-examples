package itx.examples.grpc.service.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(commandDescription = "repeatHello scenario")
public class RepeatHelloScenario {

    @Parameter(names = "-w", description = "number of warm-up hello messages")
    private int warmupCount = 1000;

    @Parameter(names = "-c", description = "number of hello messages")
    private int helloCount = 1000;

    @Parameter(names = "-m", description = "message payload")
    private String message = "world";

    public int getWarmupCount() {
        return warmupCount;
    }

    public int getHelloCount() {
        return helloCount;
    }

    public String getMessage() {
        return message;
    }
}
