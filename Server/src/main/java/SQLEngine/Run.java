package SQLEngine;

import SQLRequests.SQLFactory;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerEngine;
import ServerRequests.ServerEngineImpl;
import ServerRequests.ServerQuestion;

public class Run {
    public static void main(String[] args) {
        SQLFactory sqlFactory = new SQLFactory();
        SQLEngineImpl sqlEngine = new SQLEngineImpl(sqlFactory);
        ServerEngine serverEngine = new ServerEngineImpl(sqlEngine);
        serverEngine.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("1"));
        serverEngine.add(new ServerQuestion("czemu nei"), new ServerAnswer("2"));
        serverEngine.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("3"));
        serverEngine.add(new ServerQuestion("czemu tak "), new ServerAnswer("4"));
        serverEngine.add(new ServerQuestion("jak nam"), new ServerAnswer("5"));
        serverEngine.add(new ServerQuestion("czemu am "), new ServerAnswer("6"));

        System.out.println(serverEngine.query(new ServerQuestion("czemu tak")));
    }
}
