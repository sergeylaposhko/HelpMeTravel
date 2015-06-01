package ua.laposhko.hmt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "answer")
public class Answer implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "question_id")
    @JsonIgnore
    private Question question;
    @Column(name = "text", columnDefinition = "text")
    private String text;
    private Date date;

    @OneToMany(mappedBy = "answer", fetch = FetchType.EAGER)
    private Set<AnswerVote> votes;

    public Answer() {
    }

    public Answer(long id, User user, Question question, String text, Date date) {
        this.id = id;
        this.user = user;
        this.question = question;
        this.text = text;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<AnswerVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<AnswerVote> votes) {
        this.votes = votes;
    }
}
