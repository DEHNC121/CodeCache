package SQLRequests;

import javax.persistence.*;

@Entity
@Table(name = "questions")
public class SQLQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "keyword_number", nullable = false)
    private Long keywordNumber;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getKeywordCount() {
        return keywordNumber;
    }
    public void setKeywordCount(Long keywordNumber) {
        this.keywordNumber = keywordNumber;
    }
    public String getFull(){return "";}
}