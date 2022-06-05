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

        serverEngine.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("1"));
        serverEngine.add(new ServerQuestion("czemu nei"), new ServerAnswer("2"));
        serverEngine.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("3"));
        serverEngine.add(new ServerQuestion("czemu tak "), new ServerAnswer("4"));
        serverEngine.add(new ServerQuestion("jak nam"), new ServerAnswer("5"));
        serverEngine.add(new ServerQuestion("czemu am "), new ServerAnswer("6"));
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
