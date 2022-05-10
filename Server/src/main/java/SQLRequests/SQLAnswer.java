package SQLRequests;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "answers")
public class SQLAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", unique = true)
    private String value;

    @ManyToMany(mappedBy = "answers")
    private Set<SQLQuestion> questions = new HashSet<>();

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public Set<SQLQuestion> getQuestions() {
        return questions;
    }
    public void setQuestions(Set<SQLQuestion> questions) {
        this.questions = questions;
    }
}