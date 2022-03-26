package SQLRequests;

public class SQLFactory {
    public SQLAnswer createAnswer(String value){
        SQLAnswer answer = new SQLAnswer();
        answer.setValue(value);
        return answer;
    }
    public SQLQuestion createQuestion(){
        return new SQLQuestion();
    }
}
