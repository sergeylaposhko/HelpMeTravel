package ua.laposhko.hmt.web;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import org.springframework.beans.factory.annotation.Autowired;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.PlaceDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.city.ICityService;
import ua.laposhko.hmt.service.place.IPlaceService;
import ua.laposhko.hmt.service.user.IUserService;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Sergey Laposhko
 */
@Path("/place")
public class PlaceSevice {

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

    private static final Logger LOGGER = Logger.getLogger(PlaceSevice.class);

    @BadgerFish
    @GET
    @Path("/bycity")
    @Produces("application/json")
    public List<Place> getPlaceByCity(@QueryParam("cityId") int cityId) {
        LOGGER.debug("Prociding getPlacesByCity command with param " + cityId);
        List<Place> places = placeService.findByCityId(cityId);
        LOGGER.debug("Places count: " + places.size());
        return places;
    }

    @BadgerFish
    @GET
    @Path("/where")
    @Produces("application/json")
    public List<Place> getPlaceByKey(@QueryParam("cityId") int cityId,
                                     @QueryParam("key") String key) {
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

    @BadgerFish
    @GET
    @Path("/byid")
    @Produces("application/json")
    public Place getPlaceById(@QueryParam("id") int id) {
        LOGGER.debug("Prociding getPlacesById command with param " + id);
        Place place = placeService.findById(id);

        if (place == null) {
            LOGGER.debug("Place wasn't found with id: " + id);
        }
        return place;
    }

    @BadgerFish
    @GET
    @Path("/byuser")
    @Produces("application/json")
    public List<Place> getPlaceByUser(@QueryParam("sessionId") String sessionId) {
        LOGGER.debug("Prociding getPlaceByUser command with param " + sessionId);
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
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

    @BadgerFish
    @POST
    @Path("/add")
    @Produces("application/json")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public void addPlace(@FormDataParam("sessionId") String sessionId,
                         @FormDataParam("photo") InputStream uploadedInputStream,
                         @FormDataParam("photo") FormDataContentDisposition fileDetail,
                         @FormDataParam("cityId") int cityId, @FormDataParam("name") String name,
                         @FormDataParam("description") String description) {
        LOGGER.debug("Prociding addPlace command with param " + sessionId
                + ", " + cityId + ", " + name + ", ");
        if (sessionId == null) {
            throw new AuthorException();
        }
        SessionManager sessionManager = SessionManager.getInstance();
        if (!sessionManager.sessionExsists(sessionId)) {
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

        if (fileDetail.getFileName() == null || fileDetail.getFileName().isEmpty())
            return;
        String imageLoc = ImageSaver.savePlaceImage(uploadedInputStream, place, fileDetail.getFileName());
        place.setPhoto(imageLoc);
        placeService.update(place);
    }

}
