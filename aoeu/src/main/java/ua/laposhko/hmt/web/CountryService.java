package ua.laposhko.hmt.web;

import java.util.List;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;

import org.apache.log4j.Logger;
import org.jboss.resteasy.annotations.providers.jaxb.json.BadgerFish;

import org.springframework.beans.factory.annotation.Autowired;
import ua.laposhko.hmt.dao.CountryDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.service.country.ICountryService;
import ua.laposhko.hmt.web.exception.WrongParamException;

/**
 * @author Sergey Laposhko
 */
@Path("/country")
public class CountryService extends AbstractService {

    private static final Logger LOGGER = Logger.getLogger(CountryService.class);

    private ICountryService countryService;

    @Autowired
    public void setCountryService(ICountryService countryService) {
        this.countryService = countryService;
    }

    @BadgerFish
    @GET
    @Path("/all")
    @Produces("application/json")
    public List<Country> getAllCities(@QueryParam("from") String from,
                                      @QueryParam("to") String to) {
        LOGGER.debug("Prociding countryAll command.");
        List<Country> countries = countryService.findAll();
        LOGGER.debug("Country count: " + countries.size());
        return countries;
    }


    @BadgerFish
    @GET
    @Path("/byid")
    @Produces("application/json")
    public Country getCityByIdName(@QueryParam("id") int id) {
        LOGGER.debug("Prociding countryById command. Params: " + id);
        Country country = countryService.findById(id);
        if (country == null) {
            LOGGER.warn("Country wasn't found. Id = " + id);
        }
        return country;
    }

}
