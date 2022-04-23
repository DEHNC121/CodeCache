package ServerRequests;

import java.util.List;

public interface ServerEngine {
    void add(ServerQuestion q, ServerAnswer a);

    List<ServerAnswer> query(ServerQuestion q);
}
