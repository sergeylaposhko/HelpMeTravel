package ua.laposhko.hmt.web.exception;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import ua.laposhko.hmt.web.exception.message.SympleErrorMessage;

/**
 * @author Sergey Laposhko
 */
public class AuthorException extends WebApplicationException {

    private static final long serialVersionUID = -3893171029855358317L;

    private static final String ERROR_STRING = "The the user should be athorized.";

    public AuthorException() {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new SympleErrorMessage(ERROR_STRING))
                .type(MediaType.APPLICATION_JSON).build());
    }

}
