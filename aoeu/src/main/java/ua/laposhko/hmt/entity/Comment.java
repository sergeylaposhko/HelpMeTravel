package ua.laposhko.hmt.entity;

import javax.persistence.*;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "comment")
public class Comment {

    @Id
    @GeneratedValue
    private long id;
    @JoinColumn(name = "place_id")
    @ManyToOne
    private Place place;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String text;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
