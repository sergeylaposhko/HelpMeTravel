package ua.laposhko.hmt.dao.generic;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;

@Scope("prototype")
public class GenericHibernateDaoImpl<T extends Serializable> implements
        GenericHibernateDao<T> {

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
    public T findById(int id) {
        return (T) getCurrentSession().get(clazz, id);
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
        return sessionFactory.getCurrentSession();
    }
}
