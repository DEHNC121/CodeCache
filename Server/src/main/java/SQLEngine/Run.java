package SQLEngine;


import SQLRequests.SQLFactory;

public class Run {
    public static void main(String[] args){
        SQLFactory sqlFactory = new SQLFactory();
        SQLEngineImpl sqlEngine = new SQLEngineImpl();
        sqlEngine.add(sqlFactory.createQuestion("czemu?"), sqlFactory.createAnswer("bo tak"));
    }
}
