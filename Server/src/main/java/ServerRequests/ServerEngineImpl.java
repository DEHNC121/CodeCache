package ServerRequests;

import SQLEngine.SQLEngineImpl;
import SQLRequests.SQLQuestionAnswer;

import java.util.*;
import java.util.stream.Collectors;

public class ServerEngineImpl implements ServerEngine {

    SQLEngineImpl engine;

    public ServerEngineImpl(SQLEngineImpl engine) {
        this.engine = engine;
    }

    @Override
    public void add(ServerQuestion question, ServerAnswer a) {
        engine.add(question, a);
    }

    private List<ServerAnswer> answerFormat(List<SQLQuestionAnswer> inList, ArrayList<Long> order) {
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
            questionDataMap.get(id).add(dbKey, questionKeywordsMap.get(dbKey.getKeyword().getValue()));
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

        for (int i = 0; i < 5 && !questionDataMap.isEmpty(); i++) {

            var maxEntry = Collections.max(questionDataMap.entrySet(), Comparator.comparing((var e) -> e.getValue().getScore()));
            answers.add(maxEntry.getKey());
            questionDataMap.remove(maxEntry.getKey());
        }

        return answerFormat(engine.query(answers), answers);
    }

}
