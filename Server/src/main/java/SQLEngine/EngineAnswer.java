package SQLEngine;

import SQLRequests.SQLAnswer;

public class EngineAnswer {
    private final String value;
    private final Long id;

    public EngineAnswer(SQLAnswer sqlAnswer){
        value = sqlAnswer.getValue();
        id = sqlAnswer.getId();
    }

    public String getValue() {
        return value;
    }
    public Long getId() {
        return id;
    }
}
