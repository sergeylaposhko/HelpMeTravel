package ua.laposhko.hmt.dao.generic.user;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ua.laposhko.hmt.dao.generic.GenericHibernateDaoImpl;
import ua.laposhko.hmt.entity.User;

/**
 * Created by swift-seeker-89717 on 07.04.2015.
 */
@Repository("userDao")
public class UserDao extends GenericHibernateDaoImpl<User> implements IUserDAO<User> {

    public UserDao() {
        setClass(User.class);
    }

    @Autowired
    @Override
    public void setSessionFactory(SessionFactory sessionFactory) {
        super.setSessionFactory(sessionFactory);
    }

    @Override
    public User findByLogin(String login) {
        User user = (User) getCurrentSession()
                .createQuery("from User where login=?")
                .setString(0, login).uniqueResult();
        return user;
    }

}
