package ua.laposhko.hmt.web;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.ws.rs.Consumes;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.country.ICountryService;
import ua.laposhko.hmt.service.user.*;
import ua.laposhko.hmt.service.usercity.IUserCityService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

/**
 * @author Sergey Laposhko
 */
@Path("/city")
@Component
public class CityWebService extends AbstractService {

    private static final Logger LOGGER = Logger.getLogger(CityWebService.class);

    private ICityService<City> cityService;
    private IUserCityService userCityService;
    private IUserService<User> userService;
    private ICountryService countryService;

    private int id = new Random().nextInt();

    @Autowired
    public void setCountryService(ICountryService countryService) {
        this.countryService = countryService;
        LOGGER.debug("Setting country service to city web service" + countryService);
        LOGGER.debug("Id : " + id);
    }

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
        LOGGER.debug("Setting user service to city web service" + userService);
    }

    @Autowired
    public void setUserCityService(IUserCityService userCityService) {
        LOGGER.debug("Setting user city service to city web service " + userCityService);
        this.userCityService = userCityService;
    }

    @Autowired
    public void setCityService(ICityService cityService) {
        this.cityService = cityService;
    }

    @BadgerFish
    @GET
    @Path("/all")
    @Produces("application/json")
    public List<City> getAllCities(@QueryParam("from") String from,
                                   @QueryParam("to") String to) {
        LOGGER.debug("Prociding cityAll command. with id: " + id);
        LOGGER.debug("The country service is: " + countryService);
        List<City> cities = cityService.findAll();
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
        List<City> cities = cityService.findCityByName(name);

        cities = filter(from, to, cities);

        if (cities == null) {
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
    public List<City> getCityByIdName(@QueryParam("id") int id) {
        LOGGER.debug("Prociding cityByID command. Params: " + id);

        City city = cityService.findById(id);
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
        List<City> res = cityService.findByUser(userId);

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
                              @FormParam("cityId") int cityId) {
        LOGGER.debug("AddCity to user command started with params: "
                + sessionId + ", " + cityId);

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
            LOGGER.warn("Trying to add city from not exsisting session " + sessionId);
            throw new AuthorException();
        }

        Long userId = sessionManager.getUserId(sessionId);

        City city = cityService.findById(cityId);
        User user = userService.findById(userId.intValue());

        UserCity userCity = new UserCity();
        userCity.setCityId(city);
        userCity.setUserId(user);

        userCityService.save(userCity);

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
                        @FormDataParam("countryId") int countryId) {
        LOGGER.debug("Prociding addCity command. Params: " + name + ", "
                + sessionId + ", " + countryId);

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
            throw new AuthorException();
        }

        Country country = countryService.findById(countryId);

        City city = new City();
        city.setCountry(country);
        city.setName(name);
        city.setPhoto(ImageSaver.DEF_CITY_IMG);

        cityService.save(city);

        String imageLoc = ImageSaver.saveCityImage(uploadedInputStream, city, fileDetail.getFileName());

        city.setPhoto(imageLoc);
        cityService.update(city);
        LOGGER.debug("City is created");
    }

}
