package SQLRequests;

import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;

import java.util.List;

public interface SQLEngine {
    void add(ServerQuestion question, ServerAnswer answer);
    List<SQLQuestionAnswer> query(List<Long> questionIds);
    List<SQLQuestionKeyword> getKeywords(List<String> keywords);
}
