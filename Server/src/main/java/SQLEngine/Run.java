package SQLEngine;

import SQLRequests.SQLEngineImpl;
import SQLRequests.SQLFactory;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerEngine;
import ServerRequests.ServerEngineImpl;
import ServerRequests.ServerQuestion;

public class Run {
    public static void main(String[] args) {
        SQLFactory sqlFactory = new SQLFactory();
        SQLEngineImpl sqlEngine = new SQLEngineImpl(sqlFactory);

        sqlEngine.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("1"));
        sqlEngine.add(new ServerQuestion("czemu nei"), new ServerAnswer("2"));
        sqlEngine.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("3"));
        sqlEngine.add(new ServerQuestion("czemu tak "), new ServerAnswer("4"));
        sqlEngine.add(new ServerQuestion("jak nam"), new ServerAnswer("5"));
        sqlEngine.add(new ServerQuestion("czemu am "), new ServerAnswer("6"));
        sqlEngine.add(new ServerQuestion("czemu tam "), new ServerAnswer("7"));
        sqlEngine.add(new ServerQuestion("czemu tam "), new ServerAnswer("8"));

//        sqlEngine.remove(158L, 8L);

        ServerEngine serverEngine = new ServerEngineImpl(sqlEngine);
        System.out.println(serverEngine.query(new ServerQuestion("czemu tak")));
    }
}
