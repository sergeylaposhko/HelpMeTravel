package ua.laposhko.hmt.web;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.service.generic.GenericManager;

import javax.ws.rs.Path;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
@Controller
@RequestMapping("/country")
public class CountryWebService extends AbstractWebService {

    private static final Logger LOGGER = Logger.getLogger(CountryWebService.class);

    private GenericManager<Country> countryService;

    @Autowired
    public void setCountryService(GenericManager<Country> countryService) {
        this.countryService = countryService;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public
    @ResponseBody
    List<Country> getAllCountries(@RequestParam(value = "from", required = false) String from,
                               @RequestParam(value = "to", required = false) String to) {
        LOGGER.debug("Prociding countryAll command.");
        List<Country> countries = countryService.findAll();
        LOGGER.debug("Country count: " + countries.size());
        return countries;
    }


    @RequestMapping(value = "/byid", method = RequestMethod.GET)
    public
    @ResponseBody
    Country getCityByIdName(@RequestParam("id") long id) {
        LOGGER.debug("Prociding countryById command. Params: " + id);
        Country country = countryService.findById(id);
        if (country == null) {
            LOGGER.warn("Country wasn't found. Id = " + id);
        }
        return country;
    }

}
