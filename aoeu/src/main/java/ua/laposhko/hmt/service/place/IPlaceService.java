package ua.laposhko.hmt.service.place;

import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.service.generic.GenericManager;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
public interface IPlaceService extends GenericManager<Place> {
    List<Place> findByCityId(long cityId);
}
