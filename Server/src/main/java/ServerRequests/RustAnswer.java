package ServerRequests;

public class RustAnswer {
    private final ServerAnswer answer;
    private final ServerQuestion question;

    public RustAnswer(ServerAnswer answer, ServerQuestion question) {
        this.answer = answer;
        this.question = question;
    }

    @Override
    public String toString() {
        return "RustAnswer{" +
                "answer=" + answer.toString() +
                ", question=" + question.toString() +
                '}';
    }

    public ServerAnswer getAnswer() {
        return answer;
    }

    public ServerQuestion getQuestion() {
        return question;
    }
}
