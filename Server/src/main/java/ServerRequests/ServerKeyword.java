package ServerRequests;

import SQLRequests.SQLKeyword;

public class ServerKeyword {
    private Long position;
    private String keyword;

    public ServerKeyword(Long position, String keyword) {
        this.position = position;
        this.keyword = keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getKeyword() {
        return keyword;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
