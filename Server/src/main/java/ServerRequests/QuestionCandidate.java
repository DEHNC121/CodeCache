package ServerRequests;

import SQLEngine.EngineQuestionKeyword;

import java.awt.*;
import java.util.*;
import java.util.List;

public class QuestionCandidate {
    private final Long id;
    private Double score;
    private final List<ServerKeyword> keywords;
    private final Map<String,Long> exist;
    private final Map<String,Map<Long, Long>> correctPositionNext;
    private final Map<String,Map<Long, Long>> correctPositionPrev;


    public QuestionCandidate(Long id) {
        this.id = id;
        this.score = 0D;
        keywords = new ArrayList<>();
        exist = new HashMap<>();
        correctPositionNext = new HashMap<>();
        correctPositionPrev = new HashMap<>();
    }

    public void add(EngineQuestionKeyword keyword) {
        var skw = new ServerKeyword(keyword.getPosition(), keyword.getKeyword(), 1D / keyword.getQuestion().getKeywordCount());
        keywords.add(skw);
        if (!exist.containsKey( keyword.getKeyword().getValue())) {
            exist.put(keyword.getKeyword().getValue(), 0L);
            correctPositionNext.put(keyword.getKeyword().getValue(), new HashMap<>());
            correctPositionPrev.put(keyword.getKeyword().getValue(), new HashMap<>());
        }
    }

    public void add(EngineQuestionKeyword keyword, Long position) {
        var skw = new ServerKeyword(position, keyword.getKeyword(), 1D / keyword.getQuestion().getKeywordCount());
        keywords.add(skw);
        if (!exist.containsKey( keyword.getKeyword().getValue())) {
            exist.put(keyword.getKeyword().getValue(), 0L);
            correctPositionNext.put(keyword.getKeyword().getValue(),new HashMap<>());
            correctPositionPrev.put(keyword.getKeyword().getValue(), new HashMap<>());

        }

    }

    public Map<String, Map<Long, Long>> getCorrectPositionNext() {
        return correctPositionNext;
    }

    public Map<String, Map<Long, Long>> getCorrectPositionPrev() {
        return correctPositionPrev;
    }

    public void sort() {
        keywords.sort(new ServerKeyword.SortByPosition());
    }

    public List<ServerKeyword> getKeywords() {
        return keywords;
    }

    public void existUp(ServerKeyword keyword) {
        this.score += keyword.getBasicScore();
        exist.put(keyword.getKeyword(), exist.get(keyword.getKeyword())+1L);
    }
    public void positionUp(ServerKeyword keyword) {
        this.score += keyword.getBasicScore()/2;
    }

    public Double getScore() {
        return score;
    }

    public Map<String, Long> getExist() {
        return exist;
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
