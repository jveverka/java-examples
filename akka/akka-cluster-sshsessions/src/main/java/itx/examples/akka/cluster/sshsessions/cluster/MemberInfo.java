package itx.examples.akka.cluster.sshsessions.cluster;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by juraj on 3/18/17.
 */
public class MemberInfo {

    private String memberAddress;
    private Map<String, SessionInfo> sessions;

    public MemberInfo(String memberAddress) {
        this.memberAddress = memberAddress;
        this.sessions = new ConcurrentHashMap<>();
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public int getSessionCount() {
        return sessions.size();
    }

    public void addSession(SessionInfo sessionInfo) {
        sessions.put(sessionInfo.getSshSessionId(), sessionInfo);
    }

    public void removeSessionBySessionId(String sessionId) {
        sessions.remove(sessionId);
    }

}
