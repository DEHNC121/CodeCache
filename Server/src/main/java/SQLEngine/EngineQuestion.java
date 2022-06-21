package SQLEngine;

import SQLRequests.SQLQuestion;

public class EngineQuestion {
    private final String full;
    private final Long Id;
    private final Long keywordCount;

    public EngineQuestion(SQLQuestion sqlQuestion) {
        full = sqlQuestion.getFull();
        Id = sqlQuestion.getId();
        keywordCount = sqlQuestion.getKeywordCount();
    }

    public Long getId() {
        return Id;
    }

    public String getFull() {
        return full;
    }

    public Long getKeywordCount() {
        return keywordCount;
    }
}
