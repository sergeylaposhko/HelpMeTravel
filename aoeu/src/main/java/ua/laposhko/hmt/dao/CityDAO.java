package ua.laposhko.hmt.dao;

import java.util.List;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.City;

/**
 * The Class CityDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class CityDAO {

    /**
     * Find all cities.
     *
     * @return the list
     */
    public abstract List<City> findAllCities();
    
    /**
     * Find cities by country.
     *
     * @param countryId the country id
     * @return the list
     */
    public abstract List<City> findCitiesByCountry(long countryId);
    
    /**
     * Find city by id.
     *
     * @param id the id
     * @return the city
     */
    public abstract City findCityById(long id);
    

    /**
     * Find city by name.
     *
     * @param name the name
     * @return the list of cities
     */
    public abstract List<City> findCityByName(String name);
 
    /**
     * Creates the city.
     *
     * @param city the city
     */
    public abstract void createCity(City city) throws NoSuchEntityException;
    
    /**
     * Update city.
     *
     * @param city contains the id of the city to update and new info
     */
    public abstract void updateCity(City city) throws NoSuchEntityException;
    
    /**
     * Delete city.
     *
     * @param city contains the id of the city with we want to delete
     */
    public abstract void deleteCity(City city);
    
}
