package ua.laposhko.hmt.entity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "user_city")
public class UserCity implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @JoinColumn(name = "user_id")
    @ManyToOne
    private User userId;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City cityId;

    /**
     * @param id
     * @param userId
     * @param cityId
     */
    public UserCity(long id, User userId, City cityId) {
        super();
        this.id = id;
        this.userId = userId;
        this.cityId = cityId;
    }

    /**
     *
     */
    public UserCity() {
    }

    /**
     * @return the id
     */
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
     * @return the userId
     */
    public User getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(User userId) {
        this.userId = userId;
    }

    /**
     * @return the cityId
     */
    public City getCityId() {
        return cityId;
    }

    /**
     * @param cityId the cityId to set
     */
    public void setCityId(City cityId) {
        this.cityId = cityId;
    }

}
