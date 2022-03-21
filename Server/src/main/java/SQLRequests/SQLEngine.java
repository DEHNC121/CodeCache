package SQLRequests;

import java.util.List;

public interface SQLEngine {
    void add(SQLQuestion q, SQLAnswer a);
    List<SQLAnswer> query(SQLQuestion q);
}
