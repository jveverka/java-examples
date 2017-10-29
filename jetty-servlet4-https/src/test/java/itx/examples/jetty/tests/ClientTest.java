package itx.examples.jetty.tests;

import itx.examples.jetty.client.RestClient;
import itx.examples.jetty.common.SystemInfo;
import itx.examples.jetty.common.SystemUtils;
import itx.examples.jetty.server.Main;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class ClientTest {

    @DataProvider(name = "HttpClientData")
    public static Object[][] primeNumbers() {
        return new Object[][] {{"http://localhost:8080/system/info"}, {"https://localhost:8443/system/info"}};
    }

    @Test(dataProvider = "HttpClientData")
    public void testHttpClient(String url) throws Exception {
        Main main = new Main();
        main.runServer();
        RestClient restClient = new RestClient(url);
        SystemInfo systemInfo = restClient.getSystemInfo();
        Assert.assertNotNull(systemInfo);
        Assert.assertEquals(systemInfo.getApplicationName(), SystemUtils.APPL_NAME);
        Assert.assertEquals(systemInfo.getApplicationVersion(), SystemUtils.APPL_VERSION);
        Assert.assertTrue(systemInfo.getSystemTime() > 0);
        main.stopServer();
    }

}
