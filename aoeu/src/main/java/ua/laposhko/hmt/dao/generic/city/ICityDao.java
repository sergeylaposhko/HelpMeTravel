package ua.laposhko.hmt.dao.generic.city;

import ua.laposhko.hmt.dao.generic.GenericHibernateDao;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 22.05.2015.
 */
public interface ICityDao<T> extends GenericHibernateDao<T> {

    List<T> findByUser(long userId);

    List<T> findCityByName(String name);

}
