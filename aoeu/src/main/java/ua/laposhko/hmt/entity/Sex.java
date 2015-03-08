package ua.laposhko.hmt.entity;

/**
 * @author Sergey Laposhko
 *
 */
public class Sex {

    private long id;
    private String name;

    /**
     * @param id
     * @param name
     */
    public Sex(long id, String name) {
	super();
	this.id = id;
	this.name = name;
    }

    /**
     * 
     */
    public Sex() {
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

}
