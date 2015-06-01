package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.answer.IAnswerService;
import ua.laposhko.hmt.service.question.IQuestionService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

import java.sql.Date;
import java.util.Calendar;

/**
 * @author Sergey Laposhko
 */
@Controller
@RequestMapping(value = "/answer")
public class AnswerWebService extends AbstractWebService {

    private IUserService<User> userService;
    private IAnswerService answerService;
    private IQuestionService questionService;

    private static final Logger LOGGER = Logger.getLogger(AnswerWebService.class);

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

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    Question addAnswer(@RequestParam(value = "sessionId") String sessionId,
                     @RequestParam(value = "questionId") long questionId,
                     @RequestParam(value = "text") String text) {
        LOGGER.debug("Prociding add answer command with param " + sessionId
                + ", " + questionId);
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
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

        return questionService.findById(questionId);
    }

}
