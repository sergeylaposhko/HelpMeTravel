package ua.laposhko.hmt.service.city;

import ua.laposhko.hmt.entity.City;
import ua.laposhko.hmt.service.generic.GenericManager;

import java.util.List;

/**
 * Created by Vadym_Vlasenko on 26.05.2015.
 */
public interface ICityService<T> extends GenericManager<T> {

    List<T> findCityByName(String name);

    List<T> findByUser(long userId);

}
