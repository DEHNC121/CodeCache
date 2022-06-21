package SQLEngine;

import SQLRequests.SQLKeyword;
import SQLRequests.SQLQuestion;

public class EngineQuestionKeyword {
    private final EngineQuestion question;
    private final String keyword;
    private final Long position;

    public EngineQuestionKeyword(SQLQuestion question, SQLKeyword keyword, Long position) {
        this.question = new EngineQuestion(question);
        this.keyword = keyword.getValue();
        this.position = position;
    }

    public String getKeyword() {
        return keyword;
    }

    public EngineQuestion getQuestion() {
        return question;
    }

    public Long getPosition() {
        return position;
    }
}
