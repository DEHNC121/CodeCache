package SQLRequests;

public class SQLFactory {
    public SQLAnswer createAnswer(String value){
        SQLAnswer answer = new SQLAnswer();
        answer.setValue(value);
        return answer;
    }
    public SQLQuestion createQuestion(String value){
        SQLQuestion question = new SQLQuestion();
        question.setValue(value);
        return question;
    }
}
