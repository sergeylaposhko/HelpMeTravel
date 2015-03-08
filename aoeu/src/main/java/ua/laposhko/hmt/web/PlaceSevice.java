package ua.laposhko.hmt.web;

import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

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

import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.PlaceDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.session.SessionManager;
import ua.laposhko.hmt.web.exception.AuthorException;
import ua.laposhko.hmt.web.exception.WrongParamException;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * @author Sergey Laposhko
 *
 */
@Path("/place")
public class PlaceSevice {

    private static final Logger LOGGER = Logger.getLogger(PlaceSevice.class);

    @BadgerFish
    @GET
    @Path("/bycity")
    @Produces("application/json")
    public List<Place> getPlaceByCity(@QueryParam("cityId") String cityId) {
	LOGGER.debug("Prociding getPlacesByCity command with param " + cityId);
	long cityidParsed = getId(cityId, "cityId");
	DAOFactory factory = DAOFactory.getIntsatnce();
	PlaceDAO placeDAO = factory.getPlaceDAO();
	List<Place> places = placeDAO.findPlacesByCity(cityidParsed);
	LOGGER.debug("Places count: " + places.size());
	return places;
    }

    @BadgerFish
    @GET
    @Path("/where")
    @Produces("application/json")
    public List<Place> getPlaceByKey(@QueryParam("cityId") String cityId,
	    @QueryParam("key") String key) {
	LOGGER.debug("Prociding getPlaceByKey command with param " + cityId
		+ ", " + key);
	long cityidParsed = getId(cityId, "cityId");
	DAOFactory factory = DAOFactory.getIntsatnce();
	PlaceDAO placeDAO = factory.getPlaceDAO();
	List<Place> places = placeDAO.findPlacesByCity(cityidParsed);
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
    public Place getPlaceById(@QueryParam("id") String id) {
	LOGGER.debug("Prociding getPlacesById command with param " + id);
	long placeId = getId(id, "id");
	DAOFactory factory = DAOFactory.getIntsatnce();
	PlaceDAO placeDAO = factory.getPlaceDAO();
	Place place = placeDAO.findPlaceById(placeId);
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
	    if (place.getUserId() == userId) {
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
	    @FormDataParam("cityId") String cityId, @FormDataParam("name") String name,
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
	long cityIdParsed = getId(cityId, "cityId");
	
	Place place = new Place();
	place.setCityId(cityIdParsed);
	place.setDescription(description);
	place.setName(name);
	place.setUserId(userId);
	place.setPhoto(ImageSaver.DEF_PLACE_IMG);
	
	DAOFactory factory = DAOFactory.getIntsatnce();
	PlaceDAO placeDAO = factory.getPlaceDAO();
	try {
	    placeDAO.createPlace(place);
	} catch (NoSuchEntityException e) {
	    LOGGER.error("Cannot create place with params cityId=" + cityId + ", userId=" + userId);
	    throw new WrongParamException("cityId");
	}
	
	if(fileDetail.getFileName() == null || fileDetail.getFileName().isEmpty())
	    return;
	String imageLoc = ImageSaver.savePlaceImage(uploadedInputStream, place, fileDetail.getFileName());
	place.setPhoto(imageLoc);
	try {
	    placeDAO.updatePlace(place);
	} catch (NoSuchEntityException e) {
	    e.printStackTrace();
	    throw new RuntimeException("This shouldn't happen!!!");
	}	
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
