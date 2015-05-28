package ua.laposhko.hmt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "sex")
public class Sex {

    @Id
    @GeneratedValue
    private int id;
    private String name;

    /**
     * @param id
     * @param name
     */
    public Sex(int id, String name) {
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
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

}
