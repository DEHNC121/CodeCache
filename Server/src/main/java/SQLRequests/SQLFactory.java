package SQLRequests;

import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;

public class SQLFactory {
    public SQLAnswer createAnswer(ServerAnswer serverAnswer){
        SQLAnswer answer = new SQLAnswer();
        answer.setValue(serverAnswer.getValue());
        return answer;
    }
    public SQLQuestion createQuestion(ServerQuestion serverQuestion){
        SQLQuestion temp = new SQLQuestion();
        temp.setKeywordCount((long) serverQuestion.getKeys().size());
        long i = 0;
        for (String keyword: serverQuestion.getKeys()){
            temp.getKeywords().add(createKeywordPosition(keyword, i));
            i++;
        }
        return temp;
    }
    public SQLKeywordPosition createKeywordPosition(String keyword, Long position){
        SQLKeywordPosition temp = new SQLKeywordPosition();
        temp.setKeyword(createKeyword(keyword));
        temp.setPosition(position);
        return temp;
    }
    public SQLKeyword createKeyword(String keyword){
        SQLKeyword temp = new SQLKeyword();
        temp.setValue(keyword);
        return temp;
    }
}
