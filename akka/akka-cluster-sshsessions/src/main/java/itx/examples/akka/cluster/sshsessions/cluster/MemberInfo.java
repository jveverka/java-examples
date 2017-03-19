package itx.examples.akka.cluster.sshsessions.cluster;

/**
 * Created by juraj on 3/18/17.
 */
public class MemberInfo {

    private String memberAddress;
    private int sessions;

    public MemberInfo(String memberAddress, int sessions) {
        this.memberAddress = memberAddress;
        this.sessions = sessions;
    }

    public MemberInfo(MemberInfo memberInfo) {
        this.memberAddress = memberInfo.memberAddress;
        this.sessions = memberInfo.sessions;
    }

    public String getMemberAddress() {
        return memberAddress;
    }

    public int getSessions() {
        return sessions;
    }

    public void incrementSessionCount() {
        sessions++;
    }

    public void decrementSessionCount() {
        sessions--;
        if (sessions < 0) {
            sessions = 0;
        }
    }

}
