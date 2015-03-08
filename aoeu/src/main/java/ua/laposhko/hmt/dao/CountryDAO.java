package ua.laposhko.hmt.dao;

import java.util.List;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Country;

/**
 * The Class CountryDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class CountryDAO {

    /**
     * Find all countries.
     *
     * @return the list
     */
    public abstract List<Country> findAllCountries();

    /**
     * Find country by id.
     *
     * @param id
     *            the id
     * @return the country
     */
    public abstract Country findCountryById(long id);

    /**
     * Creates the country.
     *
     * @param country
     *            the country
     */
    public abstract void createCountry(Country country)
	    throws NoSuchEntityException;

    /**
     * Update country.
     *
     * @param country
     *            the country
     */
    public abstract void updateCountry(Country country)
	    throws NoSuchEntityException;

    /**
     * Delete country.
     *
     * @param country
     *            the country
     */
    public abstract void deleteCountry(Country country);

}
