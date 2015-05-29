package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.SexDAO;
import ua.laposhko.hmt.dao.UserDAO;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Sex;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.WrongParamException;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sergey Laposhko
 */
/*
 * 
 * //register user/register?login=
 * ''&firstName=''&lastName=''&sex='m'&countryId=''&photo=''&aboutMe=''&password=''
 * 
 * //login POST user/login?login=''&password=''
 * 
 * //logout user/logout?sessionID=''
 */
@Controller
@RequestMapping("/user")
public class UserWebService extends AbstractWebService {

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

    private static final Logger LOGGER = Logger.getLogger(UserWebService.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    public @ResponseBody
    Response registerUser(@RequestParam("login") String login,
                                 @RequestParam("firstName") String firstName,
                                 @RequestParam("lastName") String lastName,
                                 @RequestParam("sex") String sex,
                                 @RequestParam("countryId") int cityId,
                                 @RequestParam("photo") MultipartFile photoFile,
                                 @RequestParam("aboutMe") String aboutMe,
                                 @RequestParam("password") String password) throws IOException {
        LOGGER.info("Start registerUser command with params " + login + ", "
                + sex + ", " + cityId);
        // hashing incoming password
        password = String.valueOf(password.hashCode());

        DAOFactory factory = DAOFactory.getIntsatnce();
        UserDAO userDAO = factory.getUserDAO();
        User userWithCurrentLogin = userDAO.findUserByLogin(login);
        if (userWithCurrentLogin != null) {
            LOGGER.error("Try registering exsisting login " + login);
            throw new WrongParamException("login");
        }

        User newUser = new User();
        Sex userSex;

        if (sex == null) {
            userSex = getSexFromString("man", factory.getSexDAO());
        } else {
            userSex = getSexFromString(sex, factory.getSexDAO());
        }

        City city = cityService.findById(cityId);

        newUser.setAboutMe(aboutMe);
        newUser.setCity(city);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setLogin(login);
        newUser.setPhoto(ImageSaver.DEF_USER_PHOTO);
        newUser.setSex(userSex);
        newUser.setPassword(password);

        userService.save(newUser);

        if (photoFile != null) {
            String location = ImageSaver.saveUserPhoto(photoFile.getInputStream(),
                    newUser, photoFile.getName());
            newUser.setPhoto(location);
            userService.update(newUser);
        }
        return Response.status(Response.Status.OK).build();
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody Map<String, String> login(@RequestParam("login") String login,
                                     @RequestParam("password") String password) {
        LOGGER.debug("Starting loginCommand for login: " + login + password);
        password = String.valueOf(password.hashCode());
        DAOFactory factory = DAOFactory.getIntsatnce();
        UserDAO userDAO = factory.getUserDAO();
        User curUser = userDAO.findUserByLogin(login);
        if (curUser == null) {
            LOGGER.error("There is no user with login: " + login);
            List<String> wrongParamList = new ArrayList<String>();
            wrongParamList.add("login");
            wrongParamList.add("password");
            throw new WrongParamException(wrongParamList);
        }

        if (!curUser.getPassword().equals(password)) {
            LOGGER.error("Wrong password: " + password + " "
                    + curUser.getPassword());
            List<String> wrongParamList = new ArrayList<String>();
            wrongParamList.add("login");
            wrongParamList.add("password");
            throw new WrongParamException(wrongParamList);
        }

        SessionManager sessionManager = SessionManager.getInstance();
        String sessionId = sessionManager.createSession(String.valueOf(curUser
                .getId()));
        Map<String, String> res = new HashMap<String, String>();
        res.put("sessionId", sessionId);
        LOGGER.debug("Session created with sessionId: " + sessionId);
        return res;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public @ResponseBody Response logout(@FormParam("sessionId") String sessionId) {
        LOGGER.info("Start logout command withsessioinId: " + sessionId);

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
            LOGGER.error("Cannot logout missing session " + sessionId);
            throw new WrongParamException("sessionId");
        }
        sessionManager.closeSession(sessionId);
        LOGGER.info("Session is closed " + sessionId);
        return Response.status(Response.Status.OK).build();
    }

    /**
     * @param sex
     * @param sexDAO where to search sexes
     * @return
     */
    private Sex getSexFromString(String sex, SexDAO sexDAO) {
        List<Sex> sexs = sexDAO.findAllSexes();
        long manSexId = -1;
        long womanSexId = -1;
        if (sexs.get(0).getName().equals("man")) {
            manSexId = 0;
            womanSexId = 1;
        } else {
            manSexId = 1;
            womanSexId = 0;
        }
        if (sex.startsWith("w")) {
            return sexs.get((int) womanSexId);
        } else {
            return sexs.get((int) manSexId);
        }
    }

}
