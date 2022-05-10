package SQLRequests;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "questions")
public class SQLQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "keyword_count", nullable = false)
    private Long keywordCount;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "question_answer",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "answer_id") }
    )
    Set<SQLAnswer> answers = new HashSet<>();

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "question_keyword_position",
            joinColumns = { @JoinColumn(name = "question_id") },
            inverseJoinColumns = { @JoinColumn(name = "keyword_position_id") }
    )
    Set<SQLKeywordPosition> keywords = new HashSet<>();

    public String getFull(){
        ArrayList<String> temp = new ArrayList<String>(keywordCount.intValue());
        while(temp.size() < keywordCount.intValue())
            temp.add("");
        for (var keywordPosition: keywords){
            temp.set(keywordPosition.getPosition().intValue(), keywordPosition.getKeyword().getValue());
        }
        return String.join(" ", temp);
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getKeywordCount() {
        return keywordCount;
    }
    public void setKeywordCount(Long keywordNumber) {
        this.keywordCount = keywordNumber;
    }
    public Set<SQLAnswer> getAnswers() {
        return answers;
    }
    public void setAnswers(Set<SQLAnswer> answers) {
        this.answers = answers;
    }
    public Set<SQLKeywordPosition> getKeywords() {
        return keywords;
    }
    public void setKeywords(Set<SQLKeywordPosition> keywords) {
        this.keywords = keywords;
    }
}