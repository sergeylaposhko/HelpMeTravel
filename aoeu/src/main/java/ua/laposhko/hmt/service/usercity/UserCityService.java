package ua.laposhko.hmt.service.usercity;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import ua.laposhko.hmt.dao.generic.GenericHibernateDao;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.UserCity;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

/**
 * Created by Sergey on 27.05.2015.
 */
@Service("userCityService")
@Repository
public class UserCityService extends GenericManagerImpl<UserCity, GenericHibernateDao<UserCity>> implements IUserCityService {

    private SessionFactory sessionFactory;

    @Autowired
    public UserCityService(@Qualifier("genericHibernateDaoImp") GenericHibernateDaoImpl<UserCity> genericHibernateDao){
        genericHibernateDao.setClass(UserCity.class);
        super.setDao(genericHibernateDao);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

}
