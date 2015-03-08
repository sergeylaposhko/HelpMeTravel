package ua.laposhko.hmt.entity;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * @author Sergey Laposhko
 *
 */
@XmlRootElement
public class Country {

    private long id;
    private String name;

    /**
     * @param id
     * @param name
     */
    public Country(long id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    /**
     * 
     */
    public Country() {
    }

    /**
     * @return the id
     */
    @XmlElement
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
     * @return the name
     */
    @XmlElement
    public String getName() {
	return name;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
	this.name = name;
    }

}
