package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.entity.QuestionVote;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.answer.IAnswerService;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.generic.GenericManager;
import ua.laposhko.hmt.service.question.IQuestionService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

import javax.ws.rs.core.MediaType;
import java.util.Set;

/**
 * @author Sergey Laposhko
 */
@Controller
@RequestMapping("/questionVote")
public class QuestionVoteWebService extends AbstractWebService {

    private IQuestionService questionService;
    private IAnswerService answerService;
    private IUserService<User> userService;
    private ICityService<City> cityService;
    private GenericManager<QuestionVote> questionVoteService;

    @Autowired
    @Qualifier(value = "questionVoteService")
    public void setQuestionVoteService(GenericManager<QuestionVote> questionVoteService) {
        this.questionVoteService = questionVoteService;
    }

    @Autowired
    public void setCityService(ICityService<City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setUserService(IUserService<User> userService) {
        this.userService = userService;
    }

    @Autowired
    public void setAnswerService(IAnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setQuestionService(IQuestionService questionService) {
        this.questionService = questionService;
    }

    private static final Logger LOGGER = Logger
            .getLogger(QuestionVoteWebService.class);


    @RequestMapping(value = "/voteUp", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED)
    public
    @ResponseBody
    void voteQuestionUp(@RequestParam("questionId") long questionId,
                        @RequestParam("sessionId") String sessionId) {
        LOGGER.debug("Create question vote command with param " + questionId
                + ", " + sessionId + ", ...");

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);
        User user = userService.findById(userId.intValue());

        Question question = questionService.findById(questionId);

        Set<QuestionVote> votes = question.getVotes();
        boolean alreadyContainVote = false;
        if (votes != null) {
            for (QuestionVote vote : votes) {
                if (vote.getUser().getId() == user.getId()) {
                    alreadyContainVote = true;
                    LOGGER.debug("Vote already exsist for user with id: " + userId + " and question id: " + question.getId());
                    break;
                }
            }
        } else {
            LOGGER.debug("Votes for question with id " + question.getId() + " are null.");
        }
        if (!alreadyContainVote) {

            QuestionVote questionVote = new QuestionVote();
            questionVote.setUser(user);
            questionVote.setQuestion(question);

            questionVoteService.save(questionVote);

            LOGGER.debug("Question vote is created with id: " + questionVote.getId());
        } else {
            LOGGER.info("Question vote wasn't created.");
        }
    }

    @RequestMapping(value = "/voteDown", method = RequestMethod.POST)
    public
    @ResponseBody
    void voteQuestionDown(@RequestParam("questionId") long questionId,
                          @RequestParam("sessionId") String sessionId) {
        LOGGER.debug("Create question vote command with param " + questionId
                + ", " + sessionId + ", ...");

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);
        User user = userService.findById(userId.intValue());

        Question question = questionService.findById(questionId);

        Set<QuestionVote> votes = question.getVotes();
        if (votes != null) {
            for (QuestionVote vote : votes) {
                if (vote.getUser().getId() == user.getId()) {
                    questionVoteService.delete(vote);
                    LOGGER.debug("Removing question vote with  user id " + user.getId() + " and question vote id " + vote.getId());
                    return;
                }
            }
        }
        LOGGER.debug("Vote for user " + user.getId() + " and question " + question.getId() + " wasn't deleted because it doesn't exists.");
    }

}
