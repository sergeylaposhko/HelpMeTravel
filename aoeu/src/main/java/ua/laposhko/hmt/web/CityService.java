package ua.laposhko.hmt.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import ua.laposhko.hmt.dao.CityDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.UserCityDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 *
 */
@Path("/city")
public class CityService extends AbstractService {

    private static final Logger LOGGER = Logger.getLogger(CityService.class);

    @BadgerFish
    @GET
    @Path("/all")
    @Produces("application/json")
    public List<City> getAllCities(@QueryParam("from") String from,
	    @QueryParam("to") String to) {
	LOGGER.debug("Prociding cityAll command.");
	DAOFactory factory = DAOFactory.getIntsatnce();
	CityDAO cityDAO = factory.getCityDAO();
	List<City> cities = cityDAO.findAllCities();
	cities = filter(from, to, cities);
	LOGGER.debug("City count: " + cities.size());
	return cities;
    }

    @BadgerFish
    @GET
    @Path("/where")
    @Produces("application/json")
    public List<City> getCityWhere(@QueryParam("name") String name,
	    @QueryParam("from") String from, @QueryParam("to") String to) {
	LOGGER.debug("Prociding cityByNameID command. Params: " + name);
	DAOFactory factory = DAOFactory.getIntsatnce();
	CityDAO cityDAO = factory.getCityDAO();
	List<City> cities = null;
	if (!name.isEmpty()) {
	    cities = cityDAO.findCityByName(name);
	}
	cities = filter(from, to, cities);
	if(cities == null){
	    LOGGER.warn("City list is null.");
	} else if (cities.size() == 0) {
	    LOGGER.warn("No cities were found.");
	}
	return cities;
    }

    @BadgerFish
    @GET
    @Path("/byid")
    @Produces("application/json")
    public List<City> getCityByIdName(@DefaultValue("") @QueryParam("id") String id) {
	LOGGER.debug("Prociding cityByID command. Params: " + id);
	DAOFactory factory = DAOFactory.getIntsatnce();
	CityDAO cityDAO = factory.getCityDAO();
	City city = null;
	if (id != null && !id.trim().isEmpty()) {
	    try {
		city = cityDAO.findCityById(Long.valueOf(id));
	    } catch (NumberFormatException e) {
		LOGGER.error("Wrong parameter");
		throw new WrongParamException("id");
	    }
	}
	if (city == null) {
	    LOGGER.warn("City wasn't found. Id = " + id);
	}
	List<City> res = new ArrayList<City>();
	res.add(city);
	return res;
    }

    @BadgerFish
    @GET
    @Path("/byuser")
    @Produces("application/json")
    public List<City> getCityByUser(@QueryParam("userId") long userId) {
	LOGGER.debug("Prociding cityByUser command. Params: " + userId);
	DAOFactory factory = DAOFactory.getIntsatnce();
	CityDAO cityDAO = factory.getCityDAO();
	UserCityDAO userCityDAO = factory.getUserCityDAO();

	List<City> cities = null;
	List<UserCity> userCities = null;
	List<City> res = new ArrayList<City>();

	cities = cityDAO.findAllCities();
	userCities = userCityDAO.findUserCitiesByUser(userId);

	LOGGER.debug("Count of userCities for user" + userId + " is "
		+ userCities.size());

	for (int i = 0; i < cities.size(); i++) {
	    City currentCity = cities.get(i);
	    for (int j = 0; j < userCities.size(); j++) {
		if (currentCity.getId() == userCities.get(j).getCityId()) {
		    res.add(currentCity);
		    break;
		}
	    }
	}

	if (res == null || res.size() == 0) {
	    LOGGER.warn("No cities were found for user : " + userId);
	}
	return res;
    }

    @BadgerFish
    @POST
    @Path("/addToUser")
    @Produces("application/json")
    public void addCityToUser(@FormParam("sessionId") String sessionId,
	    @FormParam("cityId") long cityId) {

	LOGGER.debug("AddCity to user command started with params: "
		+ sessionId + ", " + cityId);

	SessionManager sessionManager = SessionManager.getInstance();
	if (!sessionManager.sessionExsists(sessionId)) {
	    LOGGER.warn("Trying to add city from not exsisting session " + sessionId);
	    throw new AuthorException();
	}

	Long userId = sessionManager.getUserId(sessionId);

	DAOFactory factory = DAOFactory.getIntsatnce();
	UserCityDAO userCityDAO = factory.getUserCityDAO();

	UserCity userCity = new UserCity();
	userCity.setCityId(cityId);
	userCity.setUserId(userId);

	try {
	    userCityDAO.createUserCity(userCity);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Error creating userCity with userId: " + userId
		    + ", cityId: " + cityId);

	    e.printStackTrace();
	}

	LOGGER.debug("UserCity is created");
    }

    @BadgerFish
    @POST
    @Path("/add")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addCity(@FormDataParam("sessionId") String sessionId,
	    @FormDataParam("photo") InputStream uploadedInputStream,
	    @FormDataParam("photo") FormDataContentDisposition fileDetail,
	    @FormDataParam("name") String name,
	    @FormDataParam("countryId") String countryId) {
	LOGGER.debug("Prociding addCity command. Params: " + name + ", "
		+ sessionId + ", " + countryId);

	SessionManager sessionManager = SessionManager.getInstance();
	if (!sessionManager.sessionExsists(sessionId)) {
	    throw new AuthorException();
	}

	DAOFactory factory = DAOFactory.getIntsatnce();
	CityDAO cityDAO = factory.getCityDAO();
	long countyIdParsed = -1;
	try {
	    countyIdParsed = Long.valueOf(countryId);
	} catch (Exception e) {
	    LOGGER.error("Wring param country id : " + countryId);
	    throw new WrongParamException("countryId");
	}

	City city = new City();
	city.setCountryId(countyIdParsed);
	city.setName(name);
	city.setPhoto(ImageSaver.DEF_CITY_IMG);

	try {
	    cityDAO.createCity(city);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Error creating a city. No country id :" + countryId);
	    throw new WrongParamException("countryId");
	}
	
	String imageLoc = ImageSaver.saveCityImage(uploadedInputStream, city, fileDetail.getFileName());
	city.setPhoto(imageLoc);
	try {
	    cityDAO.updateCity(city);
	} catch (NoSuchEntityException e) {
	    e.printStackTrace();
	    throw new RuntimeException("This shouldn't happen!!!");
	}

	LOGGER.debug("City is created");
    }

}
