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

    @Override
    public int remove(ServerQuestion q, ServerAnswer a) {
        return 0;
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


    void positionUpPrev(Long j,Long size,QuestionCandidate serverQuestion,ServerKeyword keyword ){
        if (!serverQuestion.getCorrectPositionPrev().get(keyword.getKeyword()).containsKey(j)){
            serverQuestion.getCorrectPositionPrev().get(keyword.getKeyword()).put(j, 0L);
        }
        if (serverQuestion.getCorrectPositionPrev().get(keyword.getKeyword()).get(j)<size){
            serverQuestion.positionUp(keyword);
        }
    }

    void positionUpNext(Long j,Long size,QuestionCandidate serverQuestion,ServerKeyword keyword ){
        if (!serverQuestion.getCorrectPositionNext().get(keyword.getKeyword()).containsKey(j)){
            serverQuestion.getCorrectPositionNext().get(keyword.getKeyword()).put(j, 0L);
        }
        if (serverQuestion.getCorrectPositionNext().get(keyword.getKeyword()).get(j)<size){
            serverQuestion.positionUp(keyword);
        }
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


        // paring SQLQuestionKeywords with serverQuestions with transformation to ServerKeywords
        var questionDataMap = new HashMap<Long, QuestionCandidate>();
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
            for (int i = 0; i < serverQuestion.getKeywords().size(); i++) {
                var keyword=serverQuestion.getKeywords().get(i);
                var word=keyword.getKeyword();
                if (questionKeywordsMap.containsKey(word)){
                    if(questionKeywordsMap.get(word).size()<serverQuestion.getExist().get(word)){
                        serverQuestion.existUp(keyword);
                    }
                    long size= question.getKeys().size();
                    for (var j:questionKeywordsMap.get(word)){
                        if (i==0){
                            if (j==0){
                                positionUpPrev(j,size,serverQuestion,keyword);
                            }
                            if (size!=j+1 && serverQuestion.getKeywords().size()!=i+1) {
                                if (Objects.equals(question.getKeys().get((int) (j + 1)), serverQuestion.getKeywords().get(i + 1).getKeyword())){
                                    positionUpNext(j,size,serverQuestion,keyword);
                                }
                            }

                        }
                        else if (i+1==serverQuestion.getKeywords().size()){
                            if(size==j+1){
                                positionUpNext(j,size,serverQuestion,keyword);
                            }
                            if (j!=0){
                                if (Objects.equals(question.getKeys().get((int) (j - 1)), serverQuestion.getKeywords().get(i - 1).getKeyword())){
                                    positionUpPrev(j,size,serverQuestion,keyword);
                                }
                            }

                        }else {
                            if (j!=0){
                                if (Objects.equals(question.getKeys().get((int) (j - 1)), serverQuestion.getKeywords().get(i - 1).getKeyword())){
                                    positionUpPrev(j,size,serverQuestion,keyword);
                                }
                            }
                            if (size!=j+1) {
                                if (Objects.equals(question.getKeys().get((int) (j + 1)), serverQuestion.getKeywords().get(i + 1).getKeyword())){
                                    positionUpNext(j,size,serverQuestion,keyword);
                                }
                            }
                        }
                    }
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
