package ua.laposhko.hmt.dao.mysql;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import ua.laposhko.hmt.dao.CityDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
public class MySQLCityDAO extends CityDAO {

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#findAllCities()
     */
    @Override
    public List<City> findAllCities() {
        List<City> cities = MySQLDAOFactory.findlAllEntities(City.class);
        return cities;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#findCitiesByCountry(long)
     */
    @Override
    public List<City> findCitiesByCountry(long countryId) {
        List<SimpleExpression> simpleExpressions = new ArrayList<SimpleExpression>();
        simpleExpressions.add(Restrictions.eq("countryId", countryId));
        List<City> cities = MySQLDAOFactory.fintEntitiesByRestrictions(
                City.class, simpleExpressions);
        return cities;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#findCityById(long)
     */
    @Override
    public City findCityById(User id) {
        return (City) MySQLDAOFactory.findEntityById(City.class, id);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#createCity(ua.laposhko.hmt.entity.City)
     */
    @Override
    public void createCity(City city) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(city);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#updateCity(ua.laposhko.hmt.entity.City)
     */
    @Override
    public void updateCity(City city) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(city);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#deleteCity(ua.laposhko.hmt.entity.City)
     */
    @Override
    public void deleteCity(City city) {
        MySQLDAOFactory.deleteObject(city);
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.CityDAO#findCityByName(java.lang.String)
     */
    @Override
    public List<City> findCityByName(String name) {
        List<SimpleExpression> simpleExpressions = new ArrayList<SimpleExpression>();
        simpleExpressions.add(Restrictions.like("name", "%" + name + "%"));
        List<City> cities = MySQLDAOFactory.fintEntitiesByRestrictions(
                City.class, simpleExpressions);
        return cities;
    }

}
