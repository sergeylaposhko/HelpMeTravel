package ua.laposhko.hmt.dao.generic.city;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.City;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 22.05.2015.
 */
@Repository("cityDao")
public class CityDao extends GenericHibernateDaoImpl<City> implements ICityDao<City> {

    public CityDao() {
        setClass(City.class);
    }

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public List<City> findByUser(long userId) {
        return getCurrentSession().createQuery("from City where user.id = ?").setLong(0, userId).list();
    }

    @Override
    public List<City> findCityByName(String name) {
        return getCurrentSession().createQuery("from City where user.name = ?").setString(0, name).list();
    }

}
