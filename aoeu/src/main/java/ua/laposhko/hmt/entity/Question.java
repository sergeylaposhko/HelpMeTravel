package ua.laposhko.hmt.entity;

import java.io.Serializable;
import java.sql.Date;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
    private City cityId;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User userId;
    private String header;
    private String text;
    private Date date;

    public Question(long id, City placeId, User userId, String header,
                    String text, Date date) {
        super();
        this.id = id;
        this.cityId = placeId;
        this.userId = userId;
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
    public City getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCity(City placeId) {
        this.cityId = placeId;
    }

    /**
     * @return the userId
     */
    @XmlElement
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUser(User userId) {
        this.userId = userId;
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

}
