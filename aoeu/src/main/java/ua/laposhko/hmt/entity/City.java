package ua.laposhko.hmt.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "city")
@XmlRootElement(name = "city")
public class City implements Serializable {

    private static final long serialVersionUID = -7171518416349772066L;

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;
    private String name;
    private String photo;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_city",
            joinColumns = {@JoinColumn(name = "city_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    @JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="id")
    private List<User> users;

    public City() {
    }

    public City(long id, Country country, String name, String photo, List<User> users) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.photo = photo;
        this.users = users;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
