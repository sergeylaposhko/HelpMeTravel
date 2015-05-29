package ua.laposhko.hmt.service.usercity;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

import java.util.List;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service("userCityService")
@Repository
public class UserCityService extends GenericManagerImpl<UserCity, GenericHibernateDao<UserCity>> implements IUserCityService {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<UserCity> findByCityId(long cityId) {
        Session currentSession = sessionFactory.getCurrentSession();
        return (List<UserCity>) currentSession.createQuery("FROM UserCity WHERE cityId=?").setLong(0, cityId).list();
    }

}
