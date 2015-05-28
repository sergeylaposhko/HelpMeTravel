package ua.laposhko.hmt.dao;

import ua.laposhko.hmt.dao.mysql.MySQLDAOFactory;

/**
 * @author Sergey Laposhko
 */
public abstract class DAOFactory {

    public abstract AnswerDAO getAnswerDAO();

    public abstract CityDAO getCityDAO();

    public abstract CommentDAO getCommentDAO();

    public abstract CountryDAO getCountryDAO();

    public abstract PlaceDAO getPlaceDAO();

    public abstract QuestionDAO getQuestionDAO();

    public abstract SexDAO getSexDAO();

    public abstract UserCityDAO getUserCityDAO();

    public abstract UserDAO getUserDAO();

    public static DAOFactory getIntsatnce() {
        return new MySQLDAOFactory();
    }

}
