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
        var serverEngineImpl=new ServerEngineImpl(sqlEngine);


        serverEngineImpl.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("1"));
        serverEngineImpl.add(new ServerQuestion("czemu nei"), new ServerAnswer("2"));
        serverEngineImpl.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("3"));
        serverEngineImpl.add(new ServerQuestion("czemu tak "), new ServerAnswer("4"));
        serverEngineImpl.add(new ServerQuestion("czemu tak "), new ServerAnswer("4.5"));
        serverEngineImpl.add(new ServerQuestion("jak nam"), new ServerAnswer("5"));
        serverEngineImpl.add(new ServerQuestion("czemu am "), new ServerAnswer("6"));
        serverEngineImpl.add(new ServerQuestion("czemu tam "), new ServerAnswer("7"));
        serverEngineImpl.add(new ServerQuestion("czemu tam "), new ServerAnswer("8"));

//        serverEngineImpl.remove(new ServerQuestion("czemu tam",158L), new ServerAnswer("8", 8L));

        ServerEngine serverEngine = new ServerEngineImpl(sqlEngine);
        System.out.println(serverEngineImpl.query(new ServerQuestion("czemu tak")));
    }
}
