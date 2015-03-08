package ua.laposhko.hmt.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.SexDAO;
import ua.laposhko.hmt.dao.UserDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Sex;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.WrongParamException;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Sergey Laposhko
 *
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
@Path("/user")
public class UserService extends AbstractService {

    private static final Logger LOGGER = Logger.getLogger(UserService.class);

    @BadgerFish
    @POST
    @Path("/register")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response registerUser(@FormDataParam("login") String login,
	    @FormDataParam("firstName") String firstName,
	    @FormDataParam("lastName") String lastName,
	    @FormDataParam("sex") String sex,
	    @FormDataParam("countryId") String countryId,
	    @FormDataParam("photo") InputStream uploadedInputStream,
	    @FormDataParam("photo") FormDataContentDisposition fileDetail,
	    @FormDataParam("aboutMe") String aboutMe,
	    @FormDataParam("password") String password) {
	LOGGER.info("Start registerUser command with params " + login + ", "
		+ sex + ", " + countryId);
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
	Long parsedCountryId;

	if (sex == null) {
	    userSex = getSexFromString("man", factory.getSexDAO());
	} else {
	    userSex = getSexFromString(sex, factory.getSexDAO());
	}
	if (countryId == null) {
	    parsedCountryId = 43L;
	} else {
	    parsedCountryId = getId(countryId, "countryId", LOGGER);
	}
	newUser.setAboutMe(aboutMe);
	newUser.setCountryId(parsedCountryId);
	newUser.setFirstName(firstName);
	newUser.setLastName(lastName);
	newUser.setLogin(login);
	newUser.setPhoto(ImageSaver.DEF_USER_PHOTO);
	newUser.setSexId(userSex.getId());
	newUser.setPassword(password);

	try {
	    userDAO.createUser(newUser);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Cannot create user with params: " + countryId + ", "
		    + sex);
	    throw new WrongParamException("countryId");
	}
	if (uploadedInputStream != null && fileDetail != null) {
	    String location = ImageSaver.saveUserPhoto(uploadedInputStream,
		    newUser, fileDetail.getFileName());
	    newUser.setPhoto(location);
	    try {
		userDAO.updateUser(newUser);
	    } catch (NoSuchEntityException e) {
		LOGGER.error("Cannot update user after creatin photo.");
		throw new RuntimeException(
			"Cannot update user after cratin photo");
	    }
	}
	return Response.status(Response.Status.OK).build();
    }

    @BadgerFish
    @POST
    @Path("/login")
    @Produces("application/json")
    public Map<String, String> login(@FormParam("login") String login,
	    @FormParam("password") String password) {
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

    @BadgerFish
    @POST
    @Path("/logout")
    @Produces("application/json")
    public Response logout(@FormParam("sessionId") String sessionId) {
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
     * @param sexDAO
     *            where to search sexes
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
