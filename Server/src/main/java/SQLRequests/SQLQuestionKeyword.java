package SQLRequests;

import javax.persistence.*;

@Entity
@Table(name = "QK", uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "keyword_id"}))
public class SQLQuestionKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id", nullable = false)
    private SQLQuestion question;

    @ManyToOne
    @JoinColumn(name = "keyword_id", nullable = false)
    private SQLKeyword keyword;

    @Column(name = "position", nullable = false)
    private Long position;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setKeyword(SQLKeyword keyword) {
        this.keyword = keyword;
    }

    public SQLKeyword getKeyword() {
        return keyword;
    }

    public void setQuestion(SQLQuestion question) {
        this.question = question;
    }

    public SQLQuestion getQuestion() {
        return question;
    }

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
