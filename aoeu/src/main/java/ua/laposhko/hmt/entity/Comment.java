package ua.laposhko.hmt.entity;

/**
 * @author Sergey Laposhko
 *
 */
public class Comment {

    private long id;
    private long placeId;
    private long userId;
    private String text;

    /**
     * @param id
     * @param placeId
     * @param userId
     * @param text
     */
    public Comment(long id, long placeId, long userId, String text) {
	super();
	this.id = id;
	this.placeId = placeId;
	this.userId = userId;
	this.text = text;
    }

    
    
    /**
     * 
     */
    public Comment() {
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
     * @return the placeId
     */
    public long getPlaceId() {
	return placeId;
    }

    /**
     * @param placeId
     *            the placeId to set
     */
    public void setPlaceId(long placeId) {
	this.placeId = placeId;
    }

    /**
     * @return the userId
     */
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

}
