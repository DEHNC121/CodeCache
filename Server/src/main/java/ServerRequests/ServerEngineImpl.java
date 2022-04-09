package ServerRequests;

import SQLEngine.SQLEngineImpl;
import SQLRequests.SQLQuestionAnswer;

import java.util.*;

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
        var questionKeywords=question.getKeyWords();
        var questionDataMap = new HashMap<Long, QuestionCandidate>();

        // paring SQLQuestionKeywords with serverQuestions
        for (var dbKey : dbKeys) {
            var id = dbKey.getQuestion().getId();
            if (!questionDataMap.containsKey(id)) {
                questionDataMap.put(id, new QuestionCandidate(id, 0L));
            }
            questionDataMap.get(id).add(dbKey);
        }

        // score serverQuestions
        for (var serverQuestion : questionDataMap.values()) {
            var previousPosition=-1;
            for (var questionKeyword : questionKeywords) {
//if (questionKeywords.co)
            }
        }

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

