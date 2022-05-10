package SQLEngine;

import SQLRequests.SQLAnswer;
import SQLRequests.SQLQuestion;

public class EngineQuestionAnswer {

    public EngineQuestionAnswer(SQLQuestion question, SQLAnswer answer){
        this.question = new EngineQuestion(question);
        this.answer = new EngineAnswer(answer);
    }

    private final EngineQuestion question;
    private final EngineAnswer answer;

    public EngineAnswer getAnswer() {
        return answer;
    }
    public EngineQuestion getQuestion() {
        return question;
    }
}
