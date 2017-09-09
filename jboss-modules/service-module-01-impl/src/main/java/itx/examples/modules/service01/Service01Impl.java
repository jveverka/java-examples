package itx.examples.modules.service01;

import com.google.common.base.MoreObjects;
import com.google.common.net.HostAndPort;

public class Service01Impl implements Service01 {

    @Override
    public String getData() {
        MoreObjects.ToStringHelper toStringHelper = MoreObjects.toStringHelper(this);
        toStringHelper.add("v1",1);
        HostAndPort localhost = HostAndPort.fromHost("localhost");
        return "dataFromService01=" + toStringHelper.toString() + " " + localhost.getHost();
    }

}
