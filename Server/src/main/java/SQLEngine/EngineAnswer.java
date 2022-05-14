package SQLEngine;

import SQLRequests.SQLAnswer;

public class EngineAnswer {
    private final String value;

    public EngineAnswer(SQLAnswer sqlAnswer){
        value = sqlAnswer.getValue();
    }

    public String getValue() {
        return value;
    }
}
