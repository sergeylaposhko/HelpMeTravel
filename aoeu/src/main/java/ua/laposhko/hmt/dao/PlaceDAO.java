package ua.laposhko.hmt.dao;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.entity.User;

import java.util.List;

/**
 * The Class PlaceDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class PlaceDAO {

    /**
     * Find all places.
     *
     * @return the list
     */
    public abstract List<Place> findAllPlaces();

    /**
     * Find places by city.
     *
     * @param cityId the city id
     * @return the list
     */
    public abstract List<Place> findPlacesByCity(User cityId);

    /**
     * Find place by id.
     *
     * @param id the id
     * @return the place
     */
    public abstract Place findPlaceById(User id);

    /**
     * Creates the place.
     *
     * @param place the place
     */
    public abstract void createPlace(Place place) throws NoSuchEntityException;

    /**
     * Update place.
     *
     * @param place the place
     */
    public abstract void updatePlace(Place place) throws NoSuchEntityException;

    /**
     * Delete place.
     *
     * @param place the place
     */
    public abstract void deletePlace(Place place);

}
