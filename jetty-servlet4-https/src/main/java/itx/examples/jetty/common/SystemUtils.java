package itx.examples.jetty.common;

import java.io.InputStream;
import java.security.KeyStore;

public final class SystemUtils {

    private SystemUtils() {
    }

    public final static String APPL_NAME = "Jetty https demo";
    public final static String APPL_VERSION = "1.0.0";

    public static SystemInfo getSystemInfo() {
        return new SystemInfo(System.currentTimeMillis(), APPL_NAME, APPL_VERSION);
    }

    public static KeyStore loadJKSKeyStore(String classPath, String password) throws Exception {
        KeyStore keyStore = KeyStore.getInstance("JKS");
        InputStream is = SystemUtils.class.getClassLoader().getResourceAsStream(classPath);
        keyStore.load(is, password.toCharArray());
        return keyStore;
    }

}
