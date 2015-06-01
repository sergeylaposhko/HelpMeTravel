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
import ua.laposhko.hmt.dao.PlaceDAO;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.place.IPlaceService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@Path("/place")
@Controller
@RequestMapping("/place")
public class PlaceWebService extends AbstractWebService {

    private IUserService<User> userService;
    private ICityService<City> cityService;
    private IPlaceService placeService;

    @Autowired
    public void setPlaceService(IPlaceService placeService) {
        this.placeService = placeService;
    }

    @Autowired
    public void setCityService(ICityService<City> cityService) {
        this.cityService = cityService;
    }

    @Autowired
    public void setUserService(IUserService<User> userService) {
        this.userService = userService;
    }

    private static final Logger LOGGER = Logger.getLogger(PlaceWebService.class);

    @RequestMapping(value = "/bycity", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Place> getPlaceByCity(@RequestParam("cityId") long cityId) {
        LOGGER.debug("Prociding getPlacesByCity command with param " + cityId);
        List<Place> places = placeService.findByCityId(cityId);
        LOGGER.debug("Places count: " + places.size());
        return places;
    }

    @RequestMapping(value = "/where", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Place> getPlaceByKey(@RequestParam("cityId") long cityId,
                              @RequestParam("key") String key) {
        LOGGER.debug("Prociding getPlaceByKey command with param " + cityId
                + ", " + key);
        List<Place> places = placeService.findByCityId(cityId);
        List<Place> res = new LinkedList<Place>();
        for (Place place : places) {
            if (place.getName().toLowerCase().contains(key.toLowerCase())) {
                res.add(0, place);
            } else if (place.getDescription().toLowerCase()
                    .contains(key.toLowerCase())) {
                res.add(place);
            }
        }
        LOGGER.debug("Places count for key " + key + " is : " + res.size());
        return res;
    }

    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public
    @ResponseBody
    Place getPlaceById(@RequestParam("id") long id) {
        LOGGER.debug("Prociding getPlacesById command with param " + id);
        Place place = placeService.findById(id);

        if (place == null) {
            LOGGER.debug("Place wasn't found with id: " + id);
        }
        return place;
    }

    @RequestMapping(value = "/byuser", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Place> getPlaceByUser(@RequestParam("sessionId") String sessionId) {
        LOGGER.debug("Prociding getPlaceByUser command with param " + sessionId);
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);

        DAOFactory factory = DAOFactory.getIntsatnce();
        PlaceDAO placeDAO = factory.getPlaceDAO();
        List<Place> places = placeDAO.findAllPlaces();
        List<Place> res = new LinkedList<Place>();
        for (Place place : places) {
            if (place.getUser().getId() == userId) {
                res.add(place);
            }
        }
        LOGGER.debug("Count of places for user " + userId + " : " + res.size());
        return res;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA)
    public void addPlace(@RequestParam("sessionId") String sessionId,
                         @RequestParam("photo") MultipartFile photoFile,
                         @RequestParam("cityId") long cityId,
                         @RequestParam("name") String name,
                         @RequestParam("description") String description) throws IOException {
        LOGGER.debug("Prociding addPlace command with param " + sessionId
                + ", " + cityId + ", " + name + ", ");
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExists(sessionId)) {
            throw new AuthorException();
        }
        Long userId = sessionManager.getUserId(sessionId);

        User user = userService.findById(userId.intValue());
        City city = cityService.findById(cityId);

        Place place = new Place();
        place.setCity(city);
        place.setDescription(description);
        place.setName(name);
        place.setUser(user);
        place.setPhoto(ImageSaver.DEF_PLACE_IMG);

        placeService.save(place);

        if (photoFile == null || photoFile.getName() == null)
            return;
        String imageLoc = ImageSaver.savePlaceImage(photoFile.getInputStream(), place, photoFile.getName());
        place.setPhoto(imageLoc);
        placeService.update(place);
    }

}
