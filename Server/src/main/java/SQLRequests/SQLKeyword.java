package SQLRequests;

import javax.persistence.*;

@Entity
@Table(name = "keywords")
public class SQLKeyword {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hibernate_sequence")
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "value", unique = true)
    private String value;

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

    @Override
    public String toString() {
        return "SQLKeyword{" +
                "value='" + value + '\'' +
                '}';
    }
}
