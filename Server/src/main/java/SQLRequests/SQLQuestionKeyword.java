package SQLRequests;

import javax.persistence.*;


public class SQLQuestionKeyword {
    private SQLQuestion question;
    private SQLKeyword keyword;
    private Long position;

    public SQLQuestionKeyword(SQLQuestion question, SQLKeyword keyword, Long position){
        this.question = question;
        this.keyword = keyword;
        this.position = position;
    }


    public void setKeyword(SQLKeyword keyword) {
        this.keyword = keyword;
    }

    public SQLKeyword getKeyword() {
        return keyword;
    }

    public void setQuestion(SQLQuestion question) {
        this.question = question;
    }

    public SQLQuestion getQuestion() {
        return question;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
