package SQLEngine;


import SQLRequests.SQLFactory;
import ServerRequests.ServerAnswer;
import ServerRequests.ServerEngine;
import ServerRequests.ServerEngineImpl;
import ServerRequests.ServerQuestion;

import java.util.ArrayList;
import java.util.List;

public class Run {
    public static void main(String[] args){
        SQLFactory sqlFactory = new SQLFactory();
        SQLEngineImpl sqlEngine = new SQLEngineImpl(sqlFactory);
        ServerEngine serverEngine = new ServerEngineImpl(sqlEngine);
        serverEngine.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("bo a"));
        serverEngine.add(new ServerQuestion("czemu nei"), new ServerAnswer("abc"));
        serverEngine.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("bdo a"));
        serverEngine.add(new ServerQuestion("czemu tak "), new ServerAnswer("bo aaa"));
        serverEngine.add(new ServerQuestion("jak nam"), new ServerAnswer("bo aaad"));
        serverEngine.add(new ServerQuestion("czemu am "), new ServerAnswer("bo saa"));
        System.out.println(serverEngine.query(new ServerQuestion("czemu tak")));
    }
}
