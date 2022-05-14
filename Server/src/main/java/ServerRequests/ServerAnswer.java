package ServerRequests;

public class ServerAnswer {
    private final String value;

    public ServerAnswer(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "ServerAnswer{" +
                "value='" + value + '\'' +
                '}';
    }
}
