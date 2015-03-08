package ua.laposhko.hmt.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergey Laposhko
 *
 */
@XmlRootElement(name = "city")
public class City implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7171518416349772066L;
    private long id;
    private long countryId;
    private String name;
    private String photo;
    
    /**
     * @param id
     * @param countryId
     * @param name
     */
    public City(long id, long countryId, String name, String photo) {
	super();
	this.id = id;
	this.countryId = countryId;
	this.name = name;
	this.photo = photo;
    }
    
    
    /**
     * 
     */
    public City() {
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
     * @return the countryId
     */
    @XmlElement
    public long getCountryId() {
        return countryId;
    }
    
    /**
     * @param countryId the countryId to set
     */
    public void setCountryId(long countryId) {
        this.countryId = countryId;
    }
    
    /**
     * @return the name
     */
    @XmlElement
    public String getName() {
        return name;
    }
    
    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }


    /**
     * @return the photo
     */
    @XmlElement
    public String getPhoto() {
        return photo;
    }


    /**
     * @param photo the photo to set
     */
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    
}
