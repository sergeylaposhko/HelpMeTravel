package ua.laposhko.hmt.dao;

import java.util.List;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Question;
import ua.laposhko.hmt.entity.User;

/**
 * The Class QuestionDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class QuestionDAO {

    /**
     * Find all questions.
     *
     * @param cityId the city id
     * @return the list
     */
    public abstract List<Question> findAllQuestions();

    /**
     * Find questions by city.
     *
     * @param cityId the city id
     * @return the list
     */
    public abstract List<Question> findQuestionsByCity(long cityId);

    /**
     * Find question by id.
     *
     * @param id the id
     * @return the question
     */
    public abstract Question findQuestionById(User id);

    /**
     * Creates the question.
     *
     * @param question the question
     */
    public abstract void createQuestion(Question question) throws NoSuchEntityException;

    /**
     * Update question.
     *
     * @param question the question
     */
    public abstract void updateQuestion(Question question) throws NoSuchEntityException;

    /**
     * Delete question.
     *
     * @param question the question
     */
    public abstract void deleteQuestion(Question question);

}
