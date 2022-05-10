package SQLRequests;

import java.util.Set;

public class SQLQuestionAnswers {

    public SQLQuestionAnswers(SQLQuestion question, Set<SQLAnswer> answers){
        this.question = question;
        this.answers = answers;
    }

    private SQLQuestion question;
    private Set<SQLAnswer> answers;

    public void setAnswers(Set<SQLAnswer> answers) {
        this.answers = answers;
    }
    public Set<SQLAnswer> getAnswers() {
        return answers;
    }
    public void setQuestion(SQLQuestion question) {
        this.question = question;
    }
    public SQLQuestion getQuestion() {
        return question;
    }
}
