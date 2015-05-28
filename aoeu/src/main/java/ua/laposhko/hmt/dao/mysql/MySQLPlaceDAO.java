package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.laposhko.hmt.dao.PlaceDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.entity.User;

/**
 * @author Sergey Laposhko
 */
public class MySQLPlaceDAO extends PlaceDAO {

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.PlaceDAO#findAllPlaces()
     */
    @Override
    public List<Place> findAllPlaces() {
        return MySQLDAOFactory.findlAllEntities(Place.class);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.PlaceDAO#findPlacesByCity()
     */
    @Override
    public List<Place> findPlacesByCity(User cityId) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("cityId", cityId));
        return MySQLDAOFactory.fintEntitiesByRestrictions(Place.class,
                expressions);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.PlaceDAO#findPlaceById()
     */
    @Override
    public Place findPlaceById(User id) {
        return (Place) MySQLDAOFactory.findEntityById(Place.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.PlaceDAO#createPlace(ua.laposhko.hmt.entity.Place)
     */
    @Override
    public void createPlace(Place place) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(place);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.PlaceDAO#updatePlace(ua.laposhko.hmt.entity.Place)
     */
    @Override
    public void updatePlace(Place place) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(place);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.PlaceDAO#deletePlace(ua.laposhko.hmt.entity.Place)
     */
    @Override
    public void deletePlace(Place place) {
        MySQLDAOFactory.deleteObject(place);
    }

}
