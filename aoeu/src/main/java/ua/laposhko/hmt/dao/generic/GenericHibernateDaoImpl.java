package ua.laposhko.hmt.dao.generic;


import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Scope("prototype")
@Component(value = "genericHibernateDaoImp")
public class GenericHibernateDaoImpl<T extends Serializable> implements
        GenericHibernateDao<T> {

    private static final Logger LOGGER = Logger.getLogger(GenericHibernateDaoImpl.class);

    protected Class<T> clazz;

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void setClass(Class<T> classToSet) {
        this.clazz = classToSet;
    }

    @SuppressWarnings("unchecked")
    @Override
    public T findById(long id) {
        LOGGER.debug("Finding entity by id : " + id);
        LOGGER.debug("Finding entity with class: " + clazz);
        Object entityById = getCurrentSession().get(clazz, id);
        LOGGER.debug("The entity with id is: " + entityById);
        return (T) entityById;
    }


    @SuppressWarnings("unchecked")
    @Override
    public List<T> findAll() {
        return (List<T>) getCurrentSession().createQuery("FROM " + clazz.getName()).list();
    }


    @Override
    public void update(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }


    @Override
    public void save(T entity) {
        getCurrentSession().saveOrUpdate(entity);
    }

    @Override
    public void delete(T entity) {
        getCurrentSession().delete(entity);
    }

    protected final Session getCurrentSession() {
        Session currentSession = sessionFactory.getCurrentSession();
        LOGGER.debug("Getting current session " + currentSession);
        return currentSession;
    }
}
