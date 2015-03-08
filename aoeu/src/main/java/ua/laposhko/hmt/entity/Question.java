package ua.laposhko.hmt.entity;

import java.sql.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergey Laposhko
 *
 */
@XmlRootElement(name = "question")
public class Question {

    private long id;
    private long cityId;
    private long userId;
    private String header;
    private String text;
    private Date date;

    /**
     * @param id
     * @param cityId
     * @param userId
     * @param text
     */
    public Question(long id, long placeId, long userId, String header,
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
     * @param id
     *            the id to set
     */
    public void setId(long id) {
	this.id = id;
    }

    /**
     * @return the cityId
     */
    @XmlElement
    public long getCityId() {
	return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(long placeId) {
	this.cityId = placeId;
    }

    /**
     * @return the userId
     */
    @XmlElement
    public long getUserId() {
	return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(long userId) {
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
     * @param text
     *            the text to set
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
     * @param date
     *            the date to set
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
