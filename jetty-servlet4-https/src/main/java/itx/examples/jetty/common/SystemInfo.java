package itx.examples.jetty.common;

public class SystemInfo {

    private Long systemTime;
    private String applicationName;
    private String applicationVersion;

    public SystemInfo() {
    }

    public SystemInfo(Long systemTime, String applicationName, String applicationVersion) {
        this.systemTime = systemTime;
        this.applicationName = applicationName;
        this.applicationVersion = applicationVersion;
    }

    public Long getSystemTime() {
        return systemTime;
    }

    public void setSystemTime(Long systemTime) {
        this.systemTime = systemTime;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationVersion() {
        return applicationVersion;
    }

    public void setApplicationVersion(String applicationVersion) {
        this.applicationVersion = applicationVersion;
    }

}
