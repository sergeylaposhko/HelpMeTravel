package ua.laposhko.hmt.service.user;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.laposhko.hmt.dao.generic.user.IUserDAO;
import ua.laposhko.hmt.dao.generic.user.UserDao;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.service.generic.GenericManagerImpl;

/**
 * Created by Vadym_Vlasenko on 26.05.2015.
 */
@Component
public class UserService extends GenericManagerImpl<User, UserDao> implements IUserService<User> {

    private SessionFactory sessionFactory;

    private static final Logger logger = Logger.getLogger(UserService.class);

    @Autowired
    public UserService(IUserDAO userDAO){
        logger.debug("UserWebService is created...");
        super.setDao(userDAO);
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        logger.debug("Injecting session factory..." + sessionFactory);
    }

    @Transactional
    @Override
    public User findByLogin(String login) {
        Session currentSession = sessionFactory.getCurrentSession();
        User user = (User) currentSession.createQuery("FROM User WHERE login=?").setString(0, login).uniqueResult();
        return user;
    }



}
