package itx.examples.jetty.tests.http20;

import itx.examples.jetty.client.RestClient20;
import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.SystemInfoService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SystemInfoTest {

    @Test
    public void getSystemInfoTest() {
        SystemInfoService systemInfoService = new RestClient20("localhost", 8443);
        SystemInfo systemInfo = systemInfoService.getSystemInfo();
        Assert.assertNotNull(systemInfo);
        Assert.assertNotNull(systemInfo.getApplicationName());
        Assert.assertNotNull(systemInfo.getApplicationVersion());
        Assert.assertNotNull(systemInfo.getSystemTime());
        Assert.assertTrue(systemInfo.getSystemTime() > 0);
    }

}
