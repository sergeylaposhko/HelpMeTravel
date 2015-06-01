package ua.laposhko.hmt.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.laposhko.hmt.web.exception.message.WrongParamMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class WrongParamException extends WebApplicationException {

    /**
     *
     */
    private static final long serialVersionUID = -6737502196644218929L;
    private static final String MESSAGE_STRING = "The query has got wrong parameters.";


    public WrongParamException(String paramName) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new WrongParamMessage(MESSAGE_STRING, paramName))
                .type(MediaType.APPLICATION_JSON).build());
    }

    public WrongParamException(List<String> wrongParams) {
        super(Response.status(Response.Status.BAD_REQUEST)
                .entity(new WrongParamMessage(MESSAGE_STRING, wrongParams))
                .type(MediaType.APPLICATION_JSON).build());
    }

}
