package ua.laposhko.hmt.web;

import java.sql.Date;
import java.util.Calendar;

import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import org.springframework.beans.factory.annotation.Autowired;
import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.answer.IAnswerService;
import ua.laposhko.hmt.service.question.IQuestionService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 */
@Path("/answer")
public class AnswerService {

    private IUserService<User> userService;
    private IAnswerService answerService;
    private IQuestionService questionService;

    private static final Logger LOGGER = Logger.getLogger(AnswerService.class);

    @Autowired
    public void setQuestionService(IQuestionService questionService) {
        this.questionService = questionService;
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAnswerService(IAnswerService answerService) {
        this.answerService = answerService;
    }

    @BadgerFish
    @POST
    @Path("/add")
    @Produces("application/json")
    public void addAnswer(@FormParam("sessionId") String sessionId,
                          @FormParam("questionId") int questionId,
                          @FormParam("text") String text) {
        LOGGER.debug("Prociding add answer command with param " + sessionId
                + ", " + questionId);
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);

        User user = userService.findById(userId.intValue());
        Question question = questionService.findById(questionId);

        Answer answer = new Answer();
        answer.setDate(new Date(Calendar.getInstance().getTimeInMillis()));
        answer.setQuestion(question);
        answer.setText(text);
        answer.setUser(user);

        answerService.save(answer);
    }

}
