package ServerRequests;

import SQLEngine.SQLEngine;
import SQLEngine.EngineQuestionAnswer;

import java.util.*;
import java.util.stream.Collectors;

public class ServerEngineImpl implements ServerEngine {

    SQLEngine engine;

    public ServerEngineImpl(SQLEngine engine) {
        this.engine = engine;
    }

    @Override
    public void add(ServerQuestion question, ServerAnswer a) {
        engine.add(question, a);
    }

    private List<RustAnswer> answerFormat(List<EngineQuestionAnswer> inList, ArrayList<Long> order) {
        var answers = new ArrayList<RustAnswer>();

        for (var i = 0; i < order.size(); i++) {
            answers.add(null);
        }

        for (var a : inList) {
            for (var i = 0; i < order.size(); i++) {
                if (Objects.equals(order.get(i), a.getQuestion().getId())) {
                    answers.set(i, new RustAnswer(new ServerAnswer(a.getAnswer().getValue()), new ServerQuestion(a.getQuestion().getFull())));
                    break;
                }
            }
        }
        return answers;
    }

    @Override
    public List<RustAnswer> query(ServerQuestion question) {
        //all questions with keywords from original question
        var dbKeys = engine.getKeywords(question.getKeys());
        var questionKeywords = question.getKeyWords();
        var questionKeywordsMap = questionKeywords.stream().collect(Collectors.toMap(ServerKeyword::getKeyword, ServerKeyword::getPosition));
        var questionDataMap = new HashMap<Long, QuestionCandidate>();

        // paring SQLQuestionKeywords with serverQuestions with transformation to ServerKeywords
        for (var dbKey : dbKeys) {
            var id = dbKey.getQuestion().getId();
            if (!questionDataMap.containsKey(id)) {
                questionDataMap.put(id, new QuestionCandidate(id));
            }
            questionDataMap.get(id).add(dbKey, questionKeywordsMap.get(dbKey.getKeyword()));
        }

        // position scoring serverQuestions
        for (var serverQuestion : questionDataMap.values()) {
            serverQuestion.sort();
            for (int i = 0; i < serverQuestion.getKeywords().size() - 1; i++) {
                if (serverQuestion.getKeywords().get(i).getPosition() + 1 == serverQuestion.getKeywords().get(i + 1).getPosition()) {
                    serverQuestion.basicUp(serverQuestion.getKeywords().get(i));
                }
            }
        }

        var answers = new ArrayList<Long>();

        //top 5 selection
        for (int i = 0; i < 5 && !questionDataMap.isEmpty(); i++) {
            var maxEntry = Collections.max(questionDataMap.entrySet(), Comparator.comparing((var e) -> e.getValue().getScore()));
            answers.add(maxEntry.getKey());
            questionDataMap.remove(maxEntry.getKey());
        }

        return answerFormat(engine.query(answers), answers);
    }

}
