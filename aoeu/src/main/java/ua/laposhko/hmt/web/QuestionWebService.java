package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.QuestionDAO;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.answer.IAnswerService;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.question.IQuestionService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

import java.sql.Date;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@Controller
@RequestMapping("/question")
public class QuestionWebService extends AbstractWebService {

    private IQuestionService questionService;
    private IAnswerService answerService;
    private IUserService<User> userService;
    private ICityService<City> cityService;

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
            .getLogger(QuestionWebService.class);

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Question> getAllQuestions() {
        LOGGER.debug("Returning all questions...");
        List<Question> all = questionService.findAll();
        LOGGER.debug("Total question count is " + all.size());
        return all;
    }

    @RequestMapping(value = "/bycity", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Question> getQuestionByCity(@RequestParam("cityId") long cityId) {
        LOGGER.debug("Prociding getQuestionByCity command with param " + cityId);

        List<Question> questions = questionService.findByCity(cityId);
        LOGGER.debug("Question count: " + questions.size());
        return questions;
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Question> getQuestionByUser(@RequestParam("userId") long userId) {
        LOGGER.debug("Prociding getQuestionByUser command with param " + userId);
        List<Question> questions = questionService.findByUserId(userId);
        LOGGER.debug("Question count: " + questions.size());
        return questions;
    }

    @RequestMapping(value = "/where", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Question> getQuestionByParams(
            @RequestParam(value = "cityId", required = false) Long cityId,
            @RequestParam("key") String keyWord) {
        LOGGER.debug("Prociding getQuestionByParams command with param "
                + cityId + ", " + keyWord);
        List<Question> questions = questionService.findAll();

        if(cityId != null){
            LOGGER.debug("City id isn't null. Filtering...");
            for (int i = 0; i < questions.size(); i++) {
                if(questions.get(i).getCity().getId() != cityId){
                    questions.remove(i);
                    i--;
                }
            }
        }

        List<Question> res = new LinkedList<>();

        for (Question curQuestion : questions) {
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

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public
    @ResponseBody
    Question getQuestionById(
            @RequestParam("id") long id) {
        LOGGER.debug("Prociding getQuestionById command with param " + id);

        Question question = questionService.findById(id);
        if (question == null) {
            throw new WrongParamException("id");
        }
        return question;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public
    @ResponseBody
    void addQuestion(@RequestParam(value = "cityId", required = false) Long cityId,
                     @RequestParam("sessionId") String sessionId,
                     @RequestParam("header") String header, @RequestParam("text") String text) {
        LOGGER.debug("Prociding addQuestion command with param " + cityId
                + ", " + sessionId + ", ...");

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);
        User user = userService.findById(userId.intValue());
        City city = null;
        if (cityId != null) {
            city = cityService.findById(cityId);
        } else {
            city = cityService.findById(2);
        }

        Question question = new Question();
        question.setDate(new Date(Calendar.getInstance().getTime().getTime()));
        question.setHeader(header);
        question.setCity(city);
        question.setText(text);
        question.setUser(user);

        questionService.save(question);

        LOGGER.debug("Question is created with id: " + question.getId());
    }

}
