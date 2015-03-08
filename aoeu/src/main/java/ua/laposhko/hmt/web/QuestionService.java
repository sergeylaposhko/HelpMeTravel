package ua.laposhko.hmt.web;

import java.sql.Date;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.QuestionDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 *
 */
@Path("/question")
public class QuestionService extends AbstractService {

    private static final Logger LOGGER = Logger
	    .getLogger(QuestionService.class);

    @BadgerFish
    @GET
    @Path("/bycity")
    @Produces("application/json")
    public List<Question> getQuestionByCity(@QueryParam("cityId") String cityId) {
	LOGGER.debug("Prociding getQuestionByCity command with param " + cityId);
	long cityidParsed = 0;
	try {
	    cityidParsed = Long.valueOf(cityId);
	} catch (NumberFormatException e) {
	    LOGGER.error("Wrong parameter " + cityId);
	    throw new WrongParamException("cityId");
	}
	DAOFactory factory = DAOFactory.getIntsatnce();
	QuestionDAO questionDAO = factory.getQuestionDAO();
	List<Question> questions = questionDAO
		.findQuestionsByCity(cityidParsed);
	LOGGER.debug("Question count: " + questions.size());
	return questions;
    }

    @BadgerFish
    @GET
    @Path("/where")
    @Produces("application/json")
    public List<Question> getQuestionByParams(
	    @QueryParam("cityId") String cityId,
	    @QueryParam("key") String keyWord) {
	LOGGER.debug("Prociding getQuestionByParams command with param "
		+ cityId + ", " + keyWord);
	long cityidParsed = 0;
	try {
	    cityidParsed = Long.valueOf(cityId);
	} catch (NumberFormatException e) {
	    LOGGER.error("Wrong parameter " + cityId);
	    throw new WrongParamException("cityId");
	}

	DAOFactory factory = DAOFactory.getIntsatnce();
	QuestionDAO questionDAO = factory.getQuestionDAO();
	List<Question> questions = questionDAO
		.findQuestionsByCity(cityidParsed);
	List<Question> res = new LinkedList<Question>();

	for (int i = 0; i < questions.size(); i++) {
	    Question curQuestion = questions.get(i);
	    if (curQuestion.getHeader().contains(keyWord)) {
		res.add(0, curQuestion);
	    } else if (curQuestion.getText().contains(keyWord)) {
		int indexOfTheLast = res.isEmpty() ? 0 : res.size() - 1;
		res.add(indexOfTheLast, curQuestion);
	    }
	}
	LOGGER.debug("Question count: " + res.size());
	return res;
    }

    @BadgerFish
    @GET
    @Path("/byid")
    @Produces("application/json")
    public Map<String, Object> getQuestionById(
	    @QueryParam("id") String idString) {
	LOGGER.debug("Prociding getQuestionById command with param " + idString);
	long questionId = 0;
	try {
	    questionId = Long.valueOf(idString);
	} catch (NumberFormatException e) {
	    LOGGER.error("Wrong parameter " + idString);
	    throw new WrongParamException("cityId");
	}
	Map<String, Object> resMap = new HashMap<String, Object>();
	DAOFactory factory = DAOFactory.getIntsatnce();
	QuestionDAO questionDAO = factory.getQuestionDAO();
	Question question = questionDAO.findQuestionById(questionId);
	if (question == null) {
	    throw new WrongParamException("id");
	}
	resMap.put("question", question);
	AnswerDAO answerDAO = factory.getAnswerDAO();
	List<Answer> answers = answerDAO.findAnswersByQuestion(questionId);
	resMap.put("answers", answers);
	LOGGER.debug("Elements count: " + answers.size());
	return resMap;
    }

    @BadgerFish
    @POST
    @Path("/add")
    @Produces("application/json")
    public void addQuestion(@FormParam("cityId") String cityId,
	    @FormParam("sessionId") String sessionId,
	    @FormParam("header") String header, @FormParam("text") String text) {
	LOGGER.debug("Prociding addQuestion command with param " + cityId
		+ ", " + sessionId + ", ...");
	
	SessionManager sessionManager = SessionManager.getInstance();
	if (!sessionManager.sessionExsists(sessionId)) {
	    throw new AuthorException();
	}
	Long userId = sessionManager.getUserId(sessionId);
	long cityidParsed = getId(cityId, "cityId", LOGGER);
	DAOFactory factory = DAOFactory.getIntsatnce();
	QuestionDAO questionDAO = factory.getQuestionDAO();
	
	Question question = new Question();
	question.setDate(new Date(Calendar.getInstance().getTime().getTime()));
	question.setHeader(header);
	question.setCityId(cityidParsed);
	question.setText(text);
	question.setUserId(userId);
	
	try {
	    questionDAO.createQuestion(question);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Error creating question with cityid = " + cityId + ", sessionid = " + sessionId);
	    throw new WrongParamException("cityId");
	}
	
	LOGGER.debug("Question is created with id: " + question.getId());
    }

}
