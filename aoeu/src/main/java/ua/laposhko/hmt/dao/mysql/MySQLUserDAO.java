package ua.laposhko.hmt.dao.mysql;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import ua.laposhko.hmt.dao.UserDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.User;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sergey Laposhko
 */
public class MySQLUserDAO extends UserDAO {

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#findlAllUsers()
     */
    @Override
    public List<User> findlAllUsers() {
        return MySQLDAOFactory.findlAllEntities(User.class);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#createUser(ua.laposhko.hmt.entity.User)
     */
    @Override
    public void createUser(User user) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(user);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#updateUser(ua.laposhko.hmt.entity.User)
     */
    @Override
    public void updateUser(User user) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(user);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#deleteUser(ua.laposhko.hmt.entity.User)
     */
    @Override
    public void deleteUser(User user) {
        MySQLDAOFactory.deleteObject(user);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#findUserById(long)
     */
    @Override
    public User findUserById(User id) {
        User user = (User) MySQLDAOFactory.findEntityById(User.class, id);
        return user;
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.UserDAO#findUserByLogin(java.lang.String)
     */
    @Override
    public User findUserByLogin(String login) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("login", login));
        List<User> res = MySQLDAOFactory.fintEntitiesByRestrictions(User.class, expressions);
        if (res.size() > 1) {
            throw new RuntimeException("Database contains more then one user with login " + login);
        }
        return res.size() == 0 ? null : res.get(0);
    }

}
