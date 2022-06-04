package ServerRequests;

public class ServerAnswer {
    private final String value;
    private final Long id;

    @Override
    public String toString() {
        return "ServerAnswer{" +
                "value='" + value + '\'' +
                ", id=" + id +
                '}';
    }

    public String getValue() {
        return value;
    }

    public Long getId() {
        return id;
    }

    public ServerAnswer(String value, Long id) {
        this.value = value;
        this.id = id;
    }

    public ServerAnswer(String value) {
        this.value = value;
        this.id = -1L;
    }
}
