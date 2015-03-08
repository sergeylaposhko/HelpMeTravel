package ua.laposhko.hmt.dao;

import java.util.List;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;

// TODO: Auto-generated Javadoc
/**
 * The Class AnswerDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class AnswerDAO {

    /**
     * Find all answers.
     *
     * @return the list
     */
    public abstract List<Answer> findAllAnswers();

    /**
     * Find answer by id.
     *
     * @param id
     *            the id
     * @return the answer
     */
    public abstract Answer findAnswerById(long id);


    /**
     * Find answers by question.
     *
     * @param questionId the question id
     * @return the list
     */
    public abstract List<Answer> findAnswersByQuestion(long questionId);

    /**
     * Creates the answer.
     *
     * @param answer
     *            the answer
     */
    public abstract void createAnswer(Answer answer) throws NoSuchEntityException;

    /**
     * Update answer.
     *
     * @param answer
     *            the answer
     */
    public abstract void updateAnswer(Answer answer) throws NoSuchEntityException;

    /**
     * Delete answer.
     *
     * @param answer
     *            the answer
     */
    public abstract void deleteAnswer(Answer answer);

}
