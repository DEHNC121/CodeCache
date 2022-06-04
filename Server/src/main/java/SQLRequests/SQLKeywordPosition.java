package SQLRequests;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "KP")
public class SQLKeywordPosition {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @ManyToMany(mappedBy = "keywords")
    private Set<SQLQuestion> questions = new HashSet<>();

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

    public Long getPosition() {
        return position;
    }

    public void setPosition(Long position) {
        this.position = position;
    }
}
