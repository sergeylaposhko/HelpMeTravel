package ua.laposhko.hmt.web.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import ua.laposhko.hmt.web.exception.message.SympleErrorMessage;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Sergey Laposhko
 */
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "You are not logged in.")
public class AuthorException extends WebApplicationException {

    private static final long serialVersionUID = -3893171029855358317L;

    private static final String ERROR_STRING = "The the user should be athorized.";

    public AuthorException() {
        super(Response.status(Response.Status.UNAUTHORIZED)
                .entity(new SympleErrorMessage(ERROR_STRING))
                .type(MediaType.APPLICATION_JSON).build());
    }

}
