package mk.edu.ukim.feit.bolt.api.models;

import javax.persistence.*;
import java.security.Timestamp;
import java.util.Date;

/**
 * Created by gjorgjim on 8/11/17.
 */
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {

    private static final int EXPIRATION = 1000 * 60 * 60 * 24;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String token;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @Column
    private Date date;

    public PasswordResetToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.date = new Date(new Date().getTime() + EXPIRATION);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


