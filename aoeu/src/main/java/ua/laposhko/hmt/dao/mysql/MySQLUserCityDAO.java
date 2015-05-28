package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.laposhko.hmt.dao.UserCityDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.entity.UserCity;

/**
 * @author Sergey Laposhko
 */
public class MySQLUserCityDAO extends UserCityDAO {

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserCityDAO#findUserCitiesByUser(long)
     */
    @Override
    public List<UserCity> findUserCitiesByUser(long userId) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("userId", userId));
        return MySQLDAOFactory.fintEntitiesByRestrictions(UserCity.class, expressions);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserCityDAO#findUserCitiesByCity(long)
     */
    @Override
    public List<UserCity> findUserCitiesByCity(User cityId) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("cityId", cityId));
        return MySQLDAOFactory.fintEntitiesByRestrictions(UserCity.class, expressions);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserCityDAO#createUserCity(ua.laposhko.hmt.entity.UserCity)
     */
    @Override
    public void createUserCity(UserCity userCity) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(userCity);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserCityDAO#updateUserCity(ua.laposhko.hmt.entity.UserCity)
     */
    @Override
    public void updateUserCity(UserCity userCity) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(userCity);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserCityDAO#deleteUserCity(ua.laposhko.hmt.entity.UserCity)
     */
    @Override
    public void deleteUserCity(UserCity userCity) {
        MySQLDAOFactory.deleteObject(userCity);
    }

}
