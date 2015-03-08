package ua.laposhko.hmt.web;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.UserCityDAO;
import ua.laposhko.hmt.dao.UserDAO;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 *
 */
@Path("/guide")
public class GuideService {

    private static final Logger LOGGER = Logger.getLogger(GuideService.class);

    @BadgerFish
    @GET
    @Path("/bycity")
    @Produces("application/json")
    public List<User> getGuideListByCity(@QueryParam("cityId") String cityId) {
	LOGGER.debug("Prociding getGuideListByCity command with param "
		+ cityId);
	long cityidParsed = getId(cityId, "cityId");
	DAOFactory factory = DAOFactory.getIntsatnce();
	UserCityDAO userCityDAO = factory.getUserCityDAO();
	UserDAO userDAO = factory.getUserDAO();

	List<UserCity> userCities = userCityDAO
		.findUserCitiesByCity(cityidParsed);
	List<User> users = new ArrayList<User>();
	for (UserCity userCity : userCities) {
	    users.add(userDAO.findUserById(userCity.getUserId()));
	}

	LOGGER.debug("Users count: " + users.size());
	return users;
    }

    @BadgerFish
    @GET
    @Path("/where")
    @Produces("application/json")
    public List<User> getGuideListByKey(@QueryParam("cityId") String cityId,
	    @QueryParam("key") String key) {
	LOGGER.debug("Prociding getGuideListByCity command with param "
		+ cityId);
	List<User> usersInTheCityList = getGuideListByCity(cityId);
	List<User> res = new LinkedList<User>();
	for(User user : usersInTheCityList){
	    if(user.getFirstName().toLowerCase().contains(key.toLowerCase()) || 
		    user.getLastName().toLowerCase().contains(key.toLowerCase())){
		res.add(user);
	    }
	}
	LOGGER.debug("Users count for key " + key + ": " + res.size());
	return res;
    }
    
    @BadgerFish
    @GET
    @Path("/byid")
    @Produces("application/json")
    public User getGuideById(@QueryParam("cityId") String cityId,
	    @QueryParam("id") String id) {
	LOGGER.debug("Prociding getGuideListByCity command with param "
		+ cityId);
	long idParsed = getId(id, "id");
	DAOFactory factory = DAOFactory.getIntsatnce();
	UserDAO userDAO = factory.getUserDAO();
	User user = userDAO.findUserById(idParsed);
	if(user == null){
	    LOGGER.warn("User wasn't found with id " + id);
	}	
	return user;
    }

    @BadgerFish
    @GET
    @Path("/bysession")
    @Produces("application/json")
    public User getGuideBySession(@QueryParam("sessionId") String sessionId) {
	LOGGER.debug("Prociding getGuideBySession command with param "
		+ sessionId);
	SessionManager sessionManager = SessionManager.getInstance();
	if(!sessionManager.sessionExsists(sessionId)){
	    LOGGER.warn("Trying to get user of unexsisting session");
	    throw new WrongParamException("sessionId");
	}
	long id = sessionManager.getUserId(sessionId);
	return getGuideById("noParam", String.valueOf(id));
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
