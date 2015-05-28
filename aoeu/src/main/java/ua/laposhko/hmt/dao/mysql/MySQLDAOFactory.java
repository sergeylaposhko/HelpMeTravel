package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.exception.ConstraintViolationException;

import ua.laposhko.hmt.config.HibernateConfig;
import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.CityDAO;
import ua.laposhko.hmt.dao.CommentDAO;
import ua.laposhko.hmt.dao.CountryDAO;
import ua.laposhko.hmt.dao.DAOFactory;
import ua.laposhko.hmt.dao.PlaceDAO;
import ua.laposhko.hmt.dao.QuestionDAO;
import ua.laposhko.hmt.dao.SexDAO;
import ua.laposhko.hmt.dao.UserCityDAO;
import ua.laposhko.hmt.dao.UserDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.User;

/**
 * @author Sergey Laposhko
 */
public class MySQLDAOFactory extends DAOFactory {

    private static final Logger LOG = Logger.getLogger(MySQLDAOFactory.class);

    private static final SessionFactory SESSION_FAC = HibernateConfig
            .getSessionFactory();

    @Override
    public AnswerDAO getAnswerDAO() {
        return new MySQLAnswerDAO();
    }

    @Override
    public CityDAO getCityDAO() {
        return new MySQLCityDAO();
    }

    @Override
    public CommentDAO getCommentDAO() {
        return new MySQLCommentDAO();
    }

    @Override
    public CountryDAO getCountryDAO() {
        return new MySQLCountryDAO();
    }

    @Override
    public PlaceDAO getPlaceDAO() {
        return new MySQLPlaceDAO();
    }

    @Override
    public QuestionDAO getQuestionDAO() {
        return new MySQlQuestionDAO();
    }

    @Override
    public SexDAO getSexDAO() {
        return new MySQLSexDAO();
    }

    @Override
    public UserCityDAO getUserCityDAO() {
        return new MySQLUserCityDAO();
    }

    @Override
    public UserDAO getUserDAO() {
        return new MySQLUserDAO();
    }

    static Session getSession() {
        LOG.debug("Creating session.");
        return SESSION_FAC.openSession();
    }

    static void saveObject(Object obj) throws NoSuchEntityException {
        Session session = null;
        try {
            session = getSession();
            session.beginTransaction();
            session.save(obj);
            session.getTransaction().commit();
        } catch (ConstraintViolationException exception) {
            LOG.error("Entity wasn't found.");
            LOG.error(exception.getMessage());
            throw new NoSuchEntityException(obj.getClass().getSimpleName());
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    static void updateObject(Object object) throws NoSuchEntityException {
        Session session = null;
        try {
            session = MySQLDAOFactory.getSession();
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    static void deleteObject(Object ob) {
        Session session = null;
        try {
            session = MySQLDAOFactory.getSession();
            session.beginTransaction();
            session.delete(ob);
            session.getTransaction().commit();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    static <T> Object findEntityById(Class<T> entityClass, User id) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("id", id));
        List<T> resList = MySQLDAOFactory.<T>fintEntitiesByRestrictions(entityClass, expressions);
        return resList.get(0);
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> findlAllEntities(Class<T> entityClass) {
        List<T> res = new ArrayList<T>();
        Session session = null;
        try {
            session = MySQLDAOFactory.getSession();
            res = session.createCriteria(entityClass).list();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return res;
    }

    @SuppressWarnings("unchecked")
    static <T> List<T> fintEntitiesByRestrictions(Class<T> entityClass,
                                                  List<SimpleExpression> restrictions) {
        Session session = null;
        List<T> answers = new ArrayList<T>();
        try {
            session = MySQLDAOFactory.getSession();
            Criteria criteria = session.createCriteria(entityClass);
            for (SimpleExpression expression : restrictions) {
                criteria.add(expression);
            }
            answers = criteria.list();
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return answers;
    }

}
