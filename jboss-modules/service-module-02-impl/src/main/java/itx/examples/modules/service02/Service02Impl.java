package itx.examples.modules.service02;


import com.google.common.base.Objects;
import com.google.common.net.HostAndPort;

public class Service02Impl implements Service02 {

    @Override
    public String getData() {
        Objects.ToStringHelper toStringHelper = Objects.toStringHelper(this);
        toStringHelper.add("v1",1);
        HostAndPort localhost = HostAndPort.fromParts("localhost", 8080);
        return "dataFromService02=" + toStringHelper.toString() + " " + localhost.getHostText();
    }

}
