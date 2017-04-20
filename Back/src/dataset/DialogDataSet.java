package dataset;

import accounts.UserDataSet;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "dialog")
public class DialogDataSet implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "text")
    private String message;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private UserDataSet user;

    public DialogDataSet() {
    }

    public DialogDataSet(String message, UserDataSet user) {
        this.message = message;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public UserDataSet getUser() {
        return user;
    }

    public void setUser(UserDataSet user) {
        this.user = user;
    }
}
