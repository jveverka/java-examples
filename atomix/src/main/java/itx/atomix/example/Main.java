package itx.atomix.example;

import io.atomix.AtomixReplica;
import io.atomix.catalyst.transport.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CompletableFuture;

public class Main {

    final private static Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        int clusterOrdinal = Utils.getClusterOrdinal(args);
        LOG.info("Atomix demo {}", clusterOrdinal);
        AtomixReplica replica = AtomixReplica.builder(new Address(Utils.HOST_NAME, Utils.DEFAULT_PORT + clusterOrdinal))
                //.withStorage(storage)
                //.withTransport(transport)
                .build();
        Runtime.getRuntime().addShutdownHook(new ShutdownHook(replica));
        if (clusterOrdinal == 0) {
            LOG.info("bootstrapping the cluster replica");
            CompletableFuture<AtomixReplica> future = replica.bootstrap();
            future.join();
        } else {
            LOG.info("starting cluster member");
            replica.join(Utils.getClusterAddresses(clusterOrdinal));
        }
    }

    private static class ShutdownHook extends Thread {
        private AtomixReplica replica;
        public ShutdownHook(AtomixReplica replica) {
            this.replica = replica;
        }
        @Override
        public void run() {
            LOG.info("Shutdown");
            replica.shutdown();
        }
    }

}
