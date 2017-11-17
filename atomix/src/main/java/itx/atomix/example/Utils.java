package itx.atomix.example;

import io.atomix.catalyst.transport.Address;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public final class Utils {

    final private static Logger LOG = LoggerFactory.getLogger(Utils.class);

    final public static String HOST_NAME = "localhost";
    final public static int DEFAULT_PORT = 8700;

    private Utils() {
    }

    public static int getClusterOrdinal(String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (Exception e) {
            return 0;
        }
    }

    public static List<Address> getClusterAddresses(int clusterOrdinal) {
        List<Address> result = new ArrayList<>();
        for (int i = 0; i<clusterOrdinal; i++) {
            LOG.info("{}:{}", HOST_NAME, DEFAULT_PORT + i);
            result.add(new Address(HOST_NAME, DEFAULT_PORT + i));
        }
        return result;
    }

}
