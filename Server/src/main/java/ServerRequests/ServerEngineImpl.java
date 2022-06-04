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

    private List<RustAnswer> answerFormat(List<SQLQuestionAnswer> inList, ArrayList<Long> order) {
        var answers = new ArrayList<RustAnswer>();

        for (var i = 0; i < order.size(); i++) {
            answers.add(null);
        }

        for (var a : inList) {
            for (var i = 0; i < order.size(); i++) {
                if (Objects.equals(order.get(i), a.getQuestion().getId())) {
                    answers.set(i, new RustAnswer(new ServerAnswer(a.getAnswer().getValue(),a.getAnswer().getId()), new ServerQuestion(a.getQuestion().getFull(), a.getQuestion().getId())));
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

        // map of position
        var questionKeywordsMap= new HashMap<String,List<Long>>();
        for (var questionKeyword : questionKeywords){
            var word = questionKeyword.getKeyword();
            if (!questionKeywordsMap.containsKey(word)) {
                questionKeywordsMap.put(word, new ArrayList<>());
            }
            questionKeywordsMap.get(word).add(questionKeyword.getPosition());
        }


        var questionDataMap = new HashMap<Long, QuestionCandidate>();

        // paring SQLQuestionKeywords with serverQuestions with transformation to ServerKeywords
        for (var dbKey : dbKeys) {
            var id = dbKey.getQuestion().getId();
            if (!questionDataMap.containsKey(id)) {
                questionDataMap.put(id, new QuestionCandidate(id));
            }
            questionDataMap.get(id).add(dbKey);
        }

        // position scoring serverQuestions
        for (var serverQuestion : questionDataMap.values()) {
            serverQuestion.sort();
//            for (int i = 0; i < serverQuestion.getKeywords().size(); i++) {
//                if (i==serverQuestion.getKeywords().size() ){
//
//                    break;
//                }
//                if (serverQuestion.getKeywords().get(i).getPosition() + 1 == serverQuestion.getKeywords().get(i + 1).getPosition()) {
//                    serverQuestion.basicUp(serverQuestion.getKeywords().get(i));
//                }
//            }
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
