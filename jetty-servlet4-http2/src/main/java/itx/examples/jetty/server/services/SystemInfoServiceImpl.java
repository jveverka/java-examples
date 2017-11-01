package itx.examples.jetty.server.services;

import itx.examples.jetty.common.dto.SystemInfo;
import itx.examples.jetty.common.services.SystemInfoService;

public class SystemInfoServiceImpl implements SystemInfoService {

    @Override
    public SystemInfo getSystemInfo() {
        return new SystemInfo(System.currentTimeMillis(), "jetty-http2-demo", "1.0.0");
    }

}
