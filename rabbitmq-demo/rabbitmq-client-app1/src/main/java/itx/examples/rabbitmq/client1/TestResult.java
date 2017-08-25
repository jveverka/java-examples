package itx.examples.rabbitmq.client1;

public class TestResult {

    private int testOrdinal;
    private float durationMs;
    private long messageCount;
    private boolean testResult;

    public TestResult(int testOrdinal, float durationMs, long messageCount, boolean testResult) {
        this.testOrdinal = testOrdinal;
        this.durationMs = durationMs;
        this.messageCount = messageCount;
        this.testResult = testResult;
    }

    public int getTestOrdinal() {
        return testOrdinal;
    }

    public float getDurationMs() {
        return durationMs;
    }

    public long getMessageCount() {
        return messageCount;
    }

    public boolean isTestResult() {
        return testResult;
    }

    public float getMessagesPerSecond() {
        return messageCount / (durationMs/1000f);
    }

    @Override
    public String toString() {
        return "TestResult{" +
                "testOrdinal=" + testOrdinal +
                ", durationMs=" + durationMs +
                ", messageCount=" + messageCount +
                ", testResult=" + testResult +
                ", msg/sec=" + getMessagesPerSecond() +
                '}';
    }
}
