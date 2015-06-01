package ua.laposhko.hmt.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.Set;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "question")
@XmlRootElement(name = "question")
public class Question implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "question")
    private Set<Answer> answers;

    private String header;
    private String text;
    private Date date;

    @OneToMany(mappedBy = "question", fetch = FetchType.EAGER)
    private Set<QuestionVote> votes;

    public Question(long id, City placeId, User user, String header,
                    String text, Date date) {
        super();
        this.id = id;
        this.city = placeId;
        this.user = user;
        this.header = header;
        this.text = text;
        this.date = date;
    }

    /**
     *
     */
    public Question() {
    }

    /**
     * @return the id
     */
    @XmlElement
    public long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(long id) {
        this.id = id;
    }

    /**
     * @return the cityId
     */
    @XmlElement
    public City getCity() {
        return city;
    }

    /**
     * @param city the cityId to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the userId
     */
    @XmlElement
    public User getUser() {
        return user;
    }

    /**
     * @return the text
     */
    @XmlElement
    public String getText() {
        return text;
    }

    /**
     * @param text the text to set
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * @return the date
     */
    @XmlElement
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * @return the header
     */
    @XmlElement
    public String getHeader() {
        return header;
    }

    /**
     * @param header the header to set
     */
    public void setHeader(String header) {
        this.header = header;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<QuestionVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<QuestionVote> votes) {
        this.votes = votes;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }
}
