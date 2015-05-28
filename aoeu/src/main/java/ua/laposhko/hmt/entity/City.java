package ua.laposhko.hmt.entity;

import java.io.Serializable;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

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
    @Column(name = "country_id")
    private Country country;
    private String name;
    private String photo;

    public City() {
    }

    public City(long id, Country country, String name, String photo) {
        this.id = id;
        this.country = country;
        this.name = name;
        this.photo = photo;
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
