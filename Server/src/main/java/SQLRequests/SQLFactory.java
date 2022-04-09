package SQLRequests;

import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;

public class SQLFactory {
    public SQLAnswer createAnswer(String value){
        SQLAnswer answer = new SQLAnswer();
        answer.setValue(value);
        return answer;
    }
    public SQLQuestion createQuestion(){
        return new SQLQuestion();
    }
    public SQLAnswer createAnswer(ServerAnswer serverAnswer){
        SQLAnswer answer = new SQLAnswer();
        answer.setValue(serverAnswer.getValue());
        return answer;
    }
    public SQLQuestion createQuestion(ServerQuestion serverQuestion){
        SQLQuestion temp = new SQLQuestion();
        temp.setKeywordCount((long) serverQuestion.getKeys().size());
        return temp;
    }
}
