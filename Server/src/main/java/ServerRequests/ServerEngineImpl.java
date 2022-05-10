package ServerRequests;

import SQLEngine.EngineQuestionAnswer;
import SQLEngine.SQLEngine;

import java.util.*;

public class ServerEngineImpl implements ServerEngine {

    SQLEngine engine;


    public ServerEngineImpl(SQLEngine engine) {
        this.engine = engine;
    }

    @Override
    public void add(ServerQuestion question, ServerAnswer a) {
        engine.add(question, a);
    }

    private class Keyword {
        private Long position;
        private String keyword;

        public Keyword(Long position, String keyword) {
            this.position = position;
            this.keyword = keyword;
        }

        public void setKeyword(String keyword) {
            this.keyword = keyword;
        }

        public String getKeyword() {
            return keyword;
        }

        public Long getPosition() {
            return position;
        }

        public void setPosition(Long position) {
            this.position = position;
        }
    }

    private class QuestionData {
        private Long id;
        private Long number;

        public QuestionData(Long id, Long number) {
            this.id = id;
            this.number = number;
        }

        public void numberUp() {
            this.number++;
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

    private List<ServerAnswer> answerFormat(List<EngineQuestionAnswer> inList, ArrayList<Long> order) {
        var answers = new ArrayList<ServerAnswer>();

        for (var i = 0; i < order.size(); i++) {
            answers.add(null);
        }

        for (var a : inList) {
            for (var i = 0; i < order.size(); i++) {
                if (Objects.equals(order.get(i), a.getQuestion().getId())) {
                    answers.set(i, new ServerAnswer(a.getAnswer().getValue()));
                    break;
                }
            }
        }
        return answers;
    }


    @Override
    public List<ServerAnswer> query(ServerQuestion question) {
        var questionKeys = question.getKeys();
        var answerKeys = engine.getKeywords(questionKeys);

        var questionMap = new HashMap<Long, List<Keyword>>();
        var questionDataMap = new HashMap<Long, QuestionData>();

        for (var answerKey : answerKeys) {
            var id = answerKey.getQuestion().getId();
            if (!questionMap.containsKey(id)) {
                questionMap.put(id, new ArrayList<Keyword>());
                questionDataMap.put(id, new QuestionData(id, 0L));
            }

            questionMap.get(id).add(new Keyword(answerKey.getPosition(), answerKey.getKeyword()));
            questionDataMap.get(id).numberUp();
            System.out.println("Denys up " + id + " " + answerKey.getKeyword());

        }
        System.out.println("Denys list " + questionDataMap);

        var answers = new ArrayList<Long>();

        for (int i = 0; i < 5 && !questionDataMap.isEmpty(); i++) {
            var maxEntry = Collections.max(questionDataMap.entrySet(),
                    Comparator.comparing((var e) -> e.getValue().getNumber()));

            System.out.println("Denys max " + maxEntry);

            answers.add(maxEntry.getKey());
            questionDataMap.remove(maxEntry.getKey());
        }

        //todo add positional relevant answers

        return answerFormat(engine.query(answers), answers);
    }

}

