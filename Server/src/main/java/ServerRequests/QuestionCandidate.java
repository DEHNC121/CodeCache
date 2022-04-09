package ServerRequests;

import SQLRequests.SQLQuestionKeyword;

import java.util.ArrayList;
import java.util.List;

public class QuestionCandidate {
    private Long id;
    private Long number;
    private List<SQLQuestionKeyword> keywords;

    public QuestionCandidate(Long id, Long number) {
        this.id = id;
        this.number = number;
        keywords= new ArrayList<>();
    }

    public void numberUp() {
        this.number++;
    }

    public void add(SQLQuestionKeyword keyword){
        keywords.add(keyword);
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "QuestionData{" +
                "number=" + number +
                '}';
    }
}
