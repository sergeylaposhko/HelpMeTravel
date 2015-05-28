package ua.laposhko.hmt.entity;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import java.io.Serializable;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "user")
@XmlRootElement
public class User implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    @ManyToOne
    @JoinColumn(name = "sex_id")
    private Sex sexId;
    @JoinColumn(name = "city_id")
    private City city;
    @JoinColumn(name = "first_name")
    private String firstName;
    @JoinColumn(name = "last_name")
    private String lastName;
    private String login;
    private String photo;
    @Column(name = "about_me")
    private String aboutMe;
    private String password;

    /**
     * @param id
     * @param sexId
     * @param city
     * @param firstName
     * @param lastName
     * @param login
     * @param photo
     */
    public User(long id, Sex sexId, City city, String firstName,
                String lastName, String login, String photo, String aboutMe, String password) {
        super();
        this.id = id;
        this.sexId = sexId;
        this.city = city;
        this.firstName = firstName;
        this.lastName = lastName;
        this.login = login;
        this.photo = photo;
        this.aboutMe = aboutMe;
        this.password = password;
    }

    /**
     *
     */
    public User() {
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
     * @return the sexId
     */
    public Sex getSexId() {
        return sexId;
    }

    /**
     * @param sexId the sexId to set
     */
    public void setSex(Sex sexId) {
        this.sexId = sexId;
    }

    /**
     * @return the city
     */
    public City getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(City city) {
        this.city = city;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * @return the lastName
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * @param lastName the lastName to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * @return the photo
     */
    public String getPhoto() {
        return photo;
    }

    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }

    /**
     * @return the aboutMe
     */
    public String getAboutMe() {
        return aboutMe;
    }

    /**
     * @param aboutMe the aboutMe to set
     */
    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

    /**
     * @return the password
     */
    @XmlTransient
    public String getPassword() {
        return password;
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = password;
    }

}
