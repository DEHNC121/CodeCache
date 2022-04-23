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
        sqlEngine.add(new ServerQuestion("czemu tak jest"), new ServerAnswer("bo a"));
        sqlEngine.add(new ServerQuestion("czemu nei"), new ServerAnswer("abc"));
        sqlEngine.add(new ServerQuestion("czemu tak nie jest"), new ServerAnswer("bdo a"));
        sqlEngine.add(new ServerQuestion("czemu tak "), new ServerAnswer("bo aaa"));
        sqlEngine.add(new ServerQuestion("jak nam"), new ServerAnswer("bo aaad"));
        sqlEngine.add(new ServerQuestion("czemu am "), new ServerAnswer("bo saa"));
        sqlEngine.add(new ServerQuestion("czemu tam "), new ServerAnswer("bo saa"));
        ServerEngine serverEngine = new ServerEngineImpl(sqlEngine);
        System.out.println(serverEngine.query(new ServerQuestion("czemu tak")));
    }
}
