package ua.laposhko.hmt.entity;

/**
 * @author Sergey Laposhko
 *
 */
public class Place {

    private long id;
    private long cityId;
    private long userId;
    private String name;
    private String description;
    private String photo;

    /**
     * @param id
     * @param cityId
     * @param userId
     * @param name
     * @param description
     * @param photo
     */
    public Place(long id, long cityId, long userId, String name,
	    String description, String photo) {
	super();
	this.id = id;
	this.cityId = cityId;
	this.userId = userId;
	this.name = name;
	this.description = description;
	this.photo = photo;
    }

    /**
     * 
     */
    public Place() {
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
     * @return the cityId
     */
    public long getCityId() {
	return cityId;
    }

    /**
     * @param cityId
     *            the cityId to set
     */
    public void setCityId(long cityId) {
	this.cityId = cityId;
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
     * @return the name
     */
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

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
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

}
