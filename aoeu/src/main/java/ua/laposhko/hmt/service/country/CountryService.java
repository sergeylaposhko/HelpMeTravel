package ua.laposhko.hmt.service.country;

import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.entity.Country;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service
public class CountryService extends GenericManagerImpl<Country, GenericHibernateDao<Country>> implements ICountryService {
}
