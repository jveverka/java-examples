package itx.examples.jetty.tests.http11;

import itx.examples.jetty.client.RestClientHttp11;
import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.SystemInfoService;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SystemInfoTest {

    @Test
    public void getSystemInfoTest() {
        SystemInfoService systemInfoService = new RestClientHttp11("http://localhost:8080");
        SystemInfo systemInfo = systemInfoService.getSystemInfo();
        Assert.assertNotNull(systemInfo);
        Assert.assertNotNull(systemInfo.getApplicationName());
        Assert.assertNotNull(systemInfo.getApplicationVersion());
        Assert.assertNotNull(systemInfo.getSystemTime());
        Assert.assertTrue(systemInfo.getSystemTime() > 0);
    }

}
