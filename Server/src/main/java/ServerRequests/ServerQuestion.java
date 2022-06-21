package ServerRequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerQuestion {
    private final String value;
    private final Long id;

    public ServerQuestion(String value, Long id) {
        this.value = value.toLowerCase();
        this.id = id;
    }

    public ServerQuestion(String value) {
        this.value = value.toLowerCase();
        this.id = -1L;
    }

    public List<String> getKeys() {
        List<String> words = new ArrayList<>(Arrays.asList(this.value.split("\\s+")));
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).replaceAll("[^\\w]", ""));
        }
        return words;
    }

    @Override
    public String toString() {
        return "ServerQuestion{" +
                "value='" + value + '\'' +
                ", id=" + id +
                '}';
    }

    public List<ServerKeyword> getKeyWords() {
        List<String> words = new ArrayList<>(Arrays.asList(this.value.split("\\s+")));
        List<ServerKeyword> keyWords = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            keyWords.add(new ServerKeyword((long) i, words.get(i).replaceAll("[^\\w]", ""), 1D / words.size()));
        }
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).replaceAll("[^\\w]", ""));
        }
        return keyWords;
    }

    public String getValue() {
        return this.value;
    }

    public Long getId() {
        return id;
    }
}
