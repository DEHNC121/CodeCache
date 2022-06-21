package ServerRequests;

import SQLRequests.SQLFactory;
import SQLRequests.SQLEngineImpl;

import java.util.List;

public class ServerEngineSingleton {
    private SQLFactory sqlFactory;
    private SQLEngineImpl sqlEngine;
    private ServerEngine serverEngine;

    private static ServerEngineSingleton instance = new ServerEngineSingleton();
    public static ServerEngineSingleton getInstance() {
        return instance;
    }

    private ServerEngineSingleton() {
        sqlFactory = new SQLFactory();
        sqlEngine = new SQLEngineImpl(sqlFactory);
        serverEngine = new ServerEngineImpl(sqlEngine);
    }

    public void add(ServerQuestion q, ServerAnswer a) {
        serverEngine.add(q, a);
    }

    public List<RustAnswer> query(ServerQuestion q) {
        return serverEngine.query(q);
    }

    public int remove(ServerQuestion q, ServerAnswer a) {
        return serverEngine.remove(q, a);
    }
}
