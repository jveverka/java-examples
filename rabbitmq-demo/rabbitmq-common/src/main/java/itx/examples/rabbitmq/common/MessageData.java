package itx.examples.rabbitmq.common;

public class MessageData {

    private long contextId;
    private String message;

    public MessageData() {
    }

    public MessageData(long contextId, String message) {
        this.contextId = contextId;
        this.message = message;
    }

    public MessageData(MessageData messageData) {
        this.contextId = messageData.getContextId();
        this.message = messageData.getMessage();
    }

    public long getContextId() {
        return contextId;
    }

    public void setContextId(Long contextId) {
        this.contextId = contextId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MessageData that = (MessageData) o;

        if (contextId != that.contextId) return false;
        return message.equals(that.message);
    }

    @Override
    public int hashCode() {
        int result = (int) (contextId ^ (contextId >>> 32));
        result = 31 * result + message.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "MessageData{" +
                "contextId=" + contextId +
                ", message='" + message + '\'' +
                '}';
    }

}
