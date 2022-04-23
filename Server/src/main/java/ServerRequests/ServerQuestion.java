package ServerRequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerQuestion {
    private final String value;

    public ServerQuestion(String value) {
        this.value = value.toLowerCase();
    }

    public List<String> getKeys() {
        List<String> words = new ArrayList<>(Arrays.asList(this.value.split("\\s+")));
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).replaceAll("[^\\w]", ""));
        }
        return words;
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
}
