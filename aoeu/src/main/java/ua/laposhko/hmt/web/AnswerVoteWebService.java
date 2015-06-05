package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.laposhko.hmt.entity.*;
import ua.laposhko.hmt.service.answer.IAnswerService;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.generic.GenericManager;
import ua.laposhko.hmt.service.question.IQuestionService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

import java.util.Set;

/**
 * @author Sergey Laposhko
 */
@Controller
@RequestMapping("/answerVote")
public class AnswerVoteWebService extends AbstractWebService {

    private IQuestionService questionService;
    private IAnswerService answerService;
    private IUserService<User> userService;
    private ICityService<City> cityService;
    private GenericManager<AnswerVote> answerVoteService;

    @Autowired
    @Qualifier(value = "answerVoteService")
    public void setAnswerVoteService(GenericManager<AnswerVote> answerVoteService) {
        this.answerVoteService = answerVoteService;
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
            .getLogger(AnswerVoteWebService.class);


    @RequestMapping(value = "/voteUp", method = RequestMethod.POST)
    public
    @ResponseBody
    void voteAnswerUp(@RequestParam("answerId") long answerId,
                     @RequestParam("sessionId") String sessionId) {
        LOGGER.debug("Create answer vote command with param " + answerId
                + ", " + sessionId + ", ...");

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);
        User user = userService.findById(userId);

        Answer answer = answerService.findById(answerId);

        Set<AnswerVote> votes = answer.getVotes();
        boolean alreadyContainVote = false;
        if(votes != null){
            for (AnswerVote vote : votes) {
                if(vote.getUser() != null && vote.getUser().getId() == user.getId()){
                    alreadyContainVote = true;
                    LOGGER.debug("Vote already exsist for user with id: " + userId + " and answer id: " + answer.getId());
                    break;
                }
            }
        } else {
            LOGGER.debug("Votes for answer are null. Id id: " + answer.getId());
        }
        if(!alreadyContainVote){

            AnswerVote answerVote = new AnswerVote();
            answerVote.setUser(user);
            answerVote.setAnswer(answer);

            answerVoteService.save(answerVote);

            LOGGER.debug("Answer vote is created with id: " + answerVote.getId());
        } else {
            LOGGER.info("Answer vote wasn't created.");
        }
    }

    @RequestMapping(value = "/voteDown", method = RequestMethod.POST)
    public
    @ResponseBody
    void voteQuestionDown(@RequestParam("answerId") long answerId,
                          @RequestParam("sessionId") String sessionId) {
        LOGGER.debug("Create answer vote down command with param " + answerId
                + ", " + sessionId + ", ...");

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);
        User user = userService.findById(userId.intValue());

        Answer answer = answerService.findById(answerId);

        Set<AnswerVote> votes = answer.getVotes();
        if (votes != null) {
            for (AnswerVote vote : votes) {
                if (vote.getUser().getId() == user.getId()) {
                    answerVoteService.delete(vote);
                    LOGGER.debug("Removing answer vote with  user id " + user.getId() + " and answer vote id " + vote.getId());
                    return;
                }
            }
        }
        LOGGER.debug("Vote for user " + user.getId() + " and answer " + answer.getId() + " wasn't deleted because it doesn't exists.");
    }

}
