package ServerRequests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServerQuestion {
    private final String value;

    public ServerQuestion(String value){
        this.value = value.toLowerCase();
    }

    public List<String> getKeys(){
        List<String> words =  new ArrayList<>(Arrays.asList(this.value.split("\\s+")));
        for (int i = 0; i < words.size(); i++) {
            words.set(i, words.get(i).replaceAll("[^\\w]", ""));
        }
        return words;
    }

    public String getValue() {
        return this.value;
    }
}
