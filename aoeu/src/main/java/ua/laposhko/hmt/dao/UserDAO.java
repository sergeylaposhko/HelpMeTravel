/*
 * 
 */
package ua.laposhko.hmt.dao;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.User;

import java.util.List;

/**
 * The Class UserDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class UserDAO {

    /**
     * Findl all users.
     *
     * @return the list
     */
    public abstract List<User> findlAllUsers();

    /**
     * Find user by id.
     *
     * @param id the id
     * @return the user
     */
    public abstract User findUserById(User id);

    /**
     * Find user by login.
     *
     * @param login the login
     * @return the user
     */
    public abstract User findUserByLogin(String login);

    /**
     * Creates the user.
     *
     * @param user the user
     */
    public abstract void createUser(User user) throws NoSuchEntityException;

    /**
     * Update user.
     *
     * @param user the user
     */
    public abstract void updateUser(User user) throws NoSuchEntityException;

    /**
     * Delete user.
     *
     * @param user the user
     */
    public abstract void deleteUser(User user);

}
