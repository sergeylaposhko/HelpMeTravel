package ua.laposhko.hmt.web.exception.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sergey Laposhko
 *
 */
@XmlRootElement(name = "error")
public class SympleErrorMessage {

    private String message;
    
    
    /**
     * Instantiates a new error message.
     *
     * @param mess the message
     */
    public SympleErrorMessage(String mess){
	message = mess;
    }

    /**
     * @return the message
     */
    @XmlElement
    public String getMessage() {
        return message;
    }

    /**
     * @param message the message to set
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
}
