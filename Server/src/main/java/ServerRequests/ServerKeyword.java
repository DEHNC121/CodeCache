package ServerRequests;

import SQLRequests.SQLKeyword;

import java.util.Comparator;

public class ServerKeyword {
    private Long position;
    private final String keyword;
    private final Double basicScore;

    public ServerKeyword(Long position, String keyword, Double basicScore) {
        this.position = position;
        this.keyword = keyword;
        this.basicScore = basicScore;
    }

    public String getKeyword() {
        return keyword;
    }

    public Long getPosition() {
        return position;
    }

    public Double getBasicScore() {
        return basicScore;
    }

    static class SortByPosition implements Comparator<ServerKeyword> {

        public int compare(ServerKeyword a, ServerKeyword b) {
            return (int) (a.getPosition() - b.getPosition());
        }
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
