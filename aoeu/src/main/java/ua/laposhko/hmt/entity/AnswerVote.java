package ua.laposhko.hmt.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Sergey on 29.05.2015.
 */
@Entity
@Table(name = "answer_vote")
public class AnswerVote implements Serializable {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name="answer_id")
    @JsonIgnore
    private Answer answer;

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

    public Answer getAnswer() {
        return answer;
    }

    public void setAnswer(Answer answer) {
        this.answer = answer;
    }
}
