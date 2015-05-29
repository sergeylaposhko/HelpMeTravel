package ua.laposhko.hmt.entity;

import javax.persistence.*;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "place")
public class Place {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String name;
    private String description;
    private String photo;

    public Place(City city, User user, String name, String description, String photo) {
        this.city = city;
        this.user = user;
        this.name = name;
        this.description = description;
        this.photo = photo;
    }

    public Place() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
