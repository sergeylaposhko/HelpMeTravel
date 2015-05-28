package ua.laposhko.hmt.dao.generic.user;


import ua.laposhko.hmt.dao.generic.GenericHibernateDao;

/**
 * Created by swift-seeker-89717 on 07.04.2015.
 */
public interface IUserDAO<T> extends GenericHibernateDao<T> {

    T findByLogin(String login);

}
