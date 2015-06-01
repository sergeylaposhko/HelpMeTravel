package ua.laposhko.hmt.dao;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.entity.User;

import java.util.List;

// TODO: Auto-generated Javadoc

/**
 * The Class AnswerDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class AnswerDAO {

    public abstract List<Answer> findAllAnswers();

    public abstract Answer findAnswerById(User id);


    /**
     * Find answers by question.
     *
     * @param questionId the question id
     * @return the list
     */
    public abstract List<Answer> findAnswersByQuestion(User questionId);

    /**
     * Creates the answer.
     *
     * @param answer the answer
     */
    public abstract void createAnswer(Answer answer) throws NoSuchEntityException;

    /**
     * Update answer.
     *
     * @param answer the answer
     */
    public abstract void updateAnswer(Answer answer) throws NoSuchEntityException;

    /**
     * Delete answer.
     *
     * @param answer the answer
     */
    public abstract void deleteAnswer(Answer answer);

}
