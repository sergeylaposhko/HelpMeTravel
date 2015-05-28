package ua.laposhko.hmt.service.city;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.dao.generic.city.CityDao;
import ua.laposhko.hmt.dao.generic.city.ICityDao;
import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 26.05.2015.
 */
@Service("cityService")
public class CityService extends GenericManagerImpl<City, CityDao> implements ICityService<City> {

    @Autowired
    @Qualifier("cityDao")
    @Override
    public void setDao(GenericHibernateDao dao) {
        super.setDao(dao);
    }

    @Transactional
    @Override
    public List<City> findCityByName(String name) {
        return dao.findCityByName(name);
    }

    @Transactional
    @Override
    public List<City> findByUser(long userId) {
        return dao.findByUser(userId);
    }
}
