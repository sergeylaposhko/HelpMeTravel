package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.generic.GenericManager;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.service.usercity.IUserCityService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@Path("/city")
@Controller
@RequestMapping(value = "/city")
public class CityWebService extends AbstractWebService {

    private static final Logger LOGGER = Logger.getLogger(CityWebService.class);

    private ICityService<City> cityService;
    private IUserCityService userCityService;
    private IUserService<User> userService;
    private GenericManager<Country> countryService;

    @Autowired
    public void setCountryService(GenericManager<Country> countryService) {
        this.countryService = countryService;
        LOGGER.debug("Setting country service to city web service" + countryService);
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

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<City> getAllCities(@RequestParam(value = "from", required = false) String from,
                            @RequestParam(value = "to", required = false) String to) {
        LOGGER.debug("Prociding cityAll command");
        LOGGER.debug("The country service is: " + countryService);
        List<City> cities = cityService.findAll();
        cities = filter(from, to, cities);
        LOGGER.debug("City count: " + cities.size());
        return cities;
    }

    @RequestMapping(value = "/where", method = RequestMethod.GET)
    public
    @ResponseBody
    List<City> getCityWhere(@RequestParam(value = "name", required = true) String name,
                            @RequestParam(value = "from", required = false) String from,
                            @RequestParam(value = "to", required = false) String to) {
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

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public
    @ResponseBody
    City getCityByIdName(@RequestParam("id") long id) {
        LOGGER.debug("Prociding cityByID command. Params: " + id);

        City city = cityService.findById(id);
        if (city == null) {
            LOGGER.warn("City wasn't found. Id = " + id);
        }
        return city;
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public
    @ResponseBody
    List<City> getCityByUser(@RequestParam("userId") long userId) {
        LOGGER.debug("Prociding cityByUser command. Params: " + userId);
        List<City> res = userService.findById(userId).getCities();

        if (res == null || res.size() == 0) {
            LOGGER.warn("No cities were found for user : " + userId);
        }
        return res;
    }

    @RequestMapping(value = "/addToUser", method = RequestMethod.POST)
    public void addCityToUser(@RequestParam("sessionId") String sessionId,
                              @RequestParam("cityId") long cityId) {
        LOGGER.debug("AddCity to user command started with params: "
                + sessionId + ", " + cityId);

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
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

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    public void addCity(@RequestParam(value = "sessionId") String sessionId,
                        @RequestParam(value = "photo") MultipartFile fileDetail,
                        @RequestParam(value = "name") String name,
                        @RequestParam(value = "countryId") long countryId) throws IOException {
        LOGGER.debug("Prociding addCity command. Params: " + name + ", "
                + sessionId + ", " + countryId);

        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }

        Country country = countryService.findById(countryId);

        City city = new City();
        city.setCountry(country);
        city.setName(name);
        city.setPhoto(ImageSaver.DEF_CITY_IMG);

        cityService.save(city);

        String imageLoc = ImageSaver.saveCityImage(fileDetail.getInputStream(), city, fileDetail.getName());

        city.setPhoto(imageLoc);
        cityService.update(city);
        LOGGER.debug("City is created");
    }

}
