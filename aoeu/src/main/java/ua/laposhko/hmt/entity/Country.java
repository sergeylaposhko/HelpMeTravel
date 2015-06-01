package ua.laposhko.hmt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;


/**
 * @author Sergey Laposhko
 */
@Entity
@Table(name = "country")
@XmlRootElement
public class Country implements Serializable {

    @Id
    @GeneratedValue
    private long id;
    private String name;

    public Country(String name) {
        this.name = name;
    }

    public Country() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
