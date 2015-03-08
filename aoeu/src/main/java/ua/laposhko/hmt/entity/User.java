package ua.laposhko.hmt.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @author Sergey Laposhko
 *
 */
@XmlRootElement
public class User {

    private long id;
    private long sexId;
    private long countryId;
    private String firstName;
    private String lastName;
    private String login;
    private String photo;
    private String aboutMe;
    private String password;

    /**
     * @param id
     * @param sexId
     * @param countryId
     * @param firstName
     * @param lastName
     * @param login
     * @param photo
     */
    public User(long id, long sexId, long countryId, String firstName,
	    String lastName, String login, String photo, String aboutMe, String password) {
	super();
	this.id = id;
	this.sexId = sexId;
	this.countryId = countryId;
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
     * @param id
     *            the id to set
     */
    public void setId(long id) {
	this.id = id;
    }

    /**
     * @return the sexId
     */
    public long getSexId() {
	return sexId;
    }

    /**
     * @param sexId
     *            the sexId to set
     */
    public void setSexId(long sexId) {
	this.sexId = sexId;
    }

    /**
     * @return the countryId
     */
    public long getCountryId() {
	return countryId;
    }

    /**
     * @param countryId
     *            the countryId to set
     */
    public void setCountryId(long countryId) {
	this.countryId = countryId;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
	return firstName;
    }

    /**
     * @param firstName
     *            the firstName to set
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
     * @param lastName
     *            the lastName to set
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
     * @param login
     *            the login to set
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
     * @param photo
     *            the photo to set
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
     * @param aboutMe
     *            the aboutMe to set
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
