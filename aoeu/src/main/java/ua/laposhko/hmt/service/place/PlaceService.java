package ua.laposhko.hmt.service.place;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.Place;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service
public class PlaceService extends GenericManagerImpl<Place, GenericHibernateDao<Place>> implements IPlaceService {

    private SessionFactory sessionFactory;

    @Autowired
    public PlaceService(@Qualifier("genericHibernateDaoImp") GenericHibernateDaoImpl<Place> genericHibernateDao){
        genericHibernateDao.setClass(Place.class);
        super.setDao(genericHibernateDao);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Place> findByCityId(long cityId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return currentSession.createQuery("FROM Place WHERE city.id=?").setLong(0, cityId).list();
    }
}
