package ua.laposhko.hmt.entity;

/**
 * @author Sergey Laposhko
 *
 */
public class UserCity {

    private long id;
    private long userId;
    private long cityId;

    /**
     * @param id
     * @param userId
     * @param cityId
     */
    public UserCity(long id, long userId, long cityId) {
	super();
	this.id = id;
	this.userId = userId;
	this.cityId = cityId;
    }

    /**
     * 
     */
    public UserCity() {
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

}
