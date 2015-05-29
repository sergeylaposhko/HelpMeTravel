package ua.laposhko.hmt.web.exception.message;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@XmlRootElement(name = "error")
public class WrongParamMessage {

    private List<String> wrongParams;

    private String message;


    public WrongParamMessage(String message, List<String> wrongParams) {
        this.message = message;
        this.wrongParams = wrongParams;
    }

    public WrongParamMessage(String message, String wrongParam) {
        this.message = message;
        this.wrongParams = new ArrayList<String>(1);
        wrongParams.add(wrongParam);
    }

    /**
     * @return the wrongParams
     */
    @XmlElement
    public List<String> getWrongParams() {
        return wrongParams;
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
