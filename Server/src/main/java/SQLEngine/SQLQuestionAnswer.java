package SQLEngine;

import SQLRequests.SQLAnswer;
import SQLRequests.SQLQuestion;

import javax.persistence.*;

@Entity
@Table(name = "QA", uniqueConstraints = @UniqueConstraint(columnNames = {"question_id", "answer_id"}))
public class SQLQuestionAnswer {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private SQLQuestion question;

    @ManyToOne
    @JoinColumn(name = "answer_id")
    private SQLAnswer answer;

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setAnswer(SQLAnswer answer) {
        this.answer = answer;
    }
    public SQLAnswer getAnswer() {
        return answer;
    }
    public void setQuestion(SQLQuestion question) {
        this.question = question;
    }
    public SQLQuestion getQuestion() {
        return question;
    }
}
