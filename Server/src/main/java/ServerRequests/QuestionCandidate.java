package ServerRequests;

import SQLRequests.SQLQuestionKeyword;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class QuestionCandidate {
    private final Long id;
    private Double score;
    private final List<ServerKeyword> keywords;

    public QuestionCandidate(Long id) {
        this.id = id;
        this.score = 0D;
        keywords = new ArrayList<>();
    }

    public void add(SQLQuestionKeyword keyword) {
        var skw = new ServerKeyword(keyword.getPosition(), keyword.getKeyword().getValue(), 1D / keyword.getQuestion().getKeywordCount());
        keywords.add(skw);
        basicUp(skw);
    }

    public void add(SQLQuestionKeyword keyword, Long position) {
        var skw = new ServerKeyword(position, keyword.getKeyword().getValue(), 1D / keyword.getQuestion().getKeywordCount());
        keywords.add(skw);
        basicUp(skw);
    }

    public void sort() {
        keywords.sort(new ServerKeyword.SortByPosition());
    }

    public List<ServerKeyword> getKeywords() {
        return keywords;
    }

    public void basicUp(ServerKeyword keyword) {
        this.score += keyword.getBasicScore();
    }

    public Double getScore() {
        return score;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "QuestionData{" +
                "score=" + score +
                '}';
    }

}
