package ua.laposhko.hmt.web.exception;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ua.laposhko.hmt.web.exception.message.WrongParamMessage;

/**
 * @author Sergey Laposhko
 *
 */
public class WrongParamException extends WebApplicationException {

    /**
     * 
     */
    private static final long serialVersionUID = -6737502196644218929L;
    private static final String MESSAGE_STRING = "The query has got wrong parameters.";
    
    
    public WrongParamException(String paramName){
	super(Response.status(Response.Status.BAD_REQUEST)
		.entity(new WrongParamMessage(MESSAGE_STRING, paramName))
		.type(MediaType.APPLICATION_JSON).build());
    }
    
    public WrongParamException(List<String> wrongParams){
	super(Response.status(Response.Status.BAD_REQUEST)
		.entity(new WrongParamMessage(MESSAGE_STRING, wrongParams))
		.type(MediaType.APPLICATION_JSON).build());
    }
    
}
