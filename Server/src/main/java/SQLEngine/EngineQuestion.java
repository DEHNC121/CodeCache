package SQLEngine;

import SQLRequests.SQLQuestion;

public class EngineQuestion {
    private final String full;
    private final Long Id;

    public EngineQuestion(SQLQuestion sqlQuestion){
        full = sqlQuestion.getFull();
        Id = sqlQuestion.getId();
    }

    public Long getId() {
        return Id;
    }
    public String getFull() {
        return full;
    }
}
