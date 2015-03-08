package ua.laposhko.hmt.web;

import java.sql.Date;
import java.util.Calendar;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 *
 */
@Path("/answer")
public class AnswerService {

    private static final Logger LOGGER = Logger.getLogger(AnswerService.class);
    
    @BadgerFish
    @POST
    @Path("/add")
    @Produces("application/json")
    public void addAnswer(@FormParam("sessionId") String sessionId,
	    @FormParam("questionId") String questionId,
	    @FormParam("text") String text) {
	LOGGER.debug("Prociding addPlace command with param " + sessionId
		+ ", " + questionId);
	if (sessionId == null) {
	    throw new AuthorException();
	}
	SessionManager sessionManager = SessionManager.getInstance();
	if (!sessionManager.sessionExsists(sessionId)) {
	    throw new AuthorException();
	}
	Long userId = sessionManager.getUserId(sessionId);
	long questionIdParsed = getId(questionId, "cityId");
	
	Answer answer = new Answer();
	answer.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
	answer.setQuestionId(questionIdParsed);
	answer.setText(text);
	answer.setUserId(userId);
	
	DAOFactory factory = DAOFactory.getIntsatnce();
	AnswerDAO answerDAO = factory.getAnswerDAO();
	
	try {
	    answerDAO.createAnswer(answer);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Cannot create place with params cityId=" + questionId + ", userId=" + userId);
	    throw new WrongParamException("cityId");
	}
    }
    
    private long getId(String idString, String paramName) {
	long res = 0;
	try {
	    res = Long.valueOf(idString);
	} catch (NumberFormatException e) {
	    LOGGER.error("Wrong parameter " + idString);
	    throw new WrongParamException(paramName);
	}
	return res;
    }
    
}
