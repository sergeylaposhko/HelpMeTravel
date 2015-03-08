package ua.laposhko.hmt.dao.mysql;

import java.util.List;

import ua.laposhko.hmt.dao.CountryDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Country;

/**
 * @author Sergey Laposhko
 *
 */
public class MySQLCountryDAO extends CountryDAO {

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CountryDAO#findAllCountries()
     */
    @Override
    public List<Country> findAllCountries() {
	return MySQLDAOFactory.findlAllEntities(Country.class);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CountryDAO#findCountryById(long)
     */
    @Override
    public Country findCountryById(long id) {
	return (Country) MySQLDAOFactory.findEntityById(Country.class, id);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CountryDAO#createCountry(ua.laposhko.hmt.entity.Country)
     */
    @Override
    public void createCountry(Country country) throws NoSuchEntityException {
	MySQLDAOFactory.saveObject(country);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CountryDAO#updateCountry(ua.laposhko.hmt.entity.Country)
     */
    @Override
    public void updateCountry(Country country) throws NoSuchEntityException {
	MySQLDAOFactory.updateObject(country);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CountryDAO#deleteCountry(ua.laposhko.hmt.entity.Country)
     */
    @Override
    public void deleteCountry(Country country) {
	MySQLDAOFactory.deleteObject(country);
    }

}
