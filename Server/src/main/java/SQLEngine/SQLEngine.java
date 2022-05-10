package SQLEngine;

import ServerRequests.ServerAnswer;
import ServerRequests.ServerQuestion;

import java.util.List;

public interface SQLEngine {
    void add(ServerQuestion question, ServerAnswer answer);
    List<EngineQuestionAnswer> query(List<Long> questionIds);
    List<EngineQuestionKeyword> getKeywords(List<String> keywords);
}
