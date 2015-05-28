package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.laposhko.hmt.dao.AnswerDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Answer;
import ua.laposhko.hmt.entity.User;

/**
 * @author Sergey Laposhko
 */
public class MySQLAnswerDAO extends AnswerDAO {

    private static final Logger LOG = Logger.getLogger(MySQLAnswerDAO.class);

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.AnswerDAO#findAllAnswers()
     */
    @Override
    public List<Answer> findAllAnswers() {
        List<Answer> answers = MySQLDAOFactory.findlAllEntities(Answer.class);
        return answers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.AnswerDAO#findAnswerById(long)
     */
    @Override
    public Answer findAnswerById(User id) {
        Answer answer = (Answer) MySQLDAOFactory.findEntityById(Answer.class, id);
        return answer;
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.dao.AnswerDAO#findAnswerByQuestion(long)
     */
    @Override
    public List<Answer> findAnswersByQuestion(User questionId) {
        List<SimpleExpression> simpleExpressions = new ArrayList<SimpleExpression>();
        simpleExpressions.add(Restrictions.eq("questionId", questionId));
        List<Answer> answers = MySQLDAOFactory.fintEntitiesByRestrictions(Answer.class, simpleExpressions);
        if (answers.size() == 0) {
            LOG.info("Answers for question empty empty: " + questionId);
        }
        return answers;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.AnswerDAO#createAnswer(ua.laposhko.hmt.entity.Answer)
     */
    @Override
    public void createAnswer(Answer answer) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(answer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.AnswerDAO#updateAnswer(ua.laposhko.hmt.entity.Answer)
     */
    @Override
    public void updateAnswer(Answer answer) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(answer);
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.dao.AnswerDAO#deleteAnswer(ua.laposhko.hmt.entity.Answer)
     */
    @Override
    public void deleteAnswer(Answer answer) {
        MySQLDAOFactory.deleteObject(answer);
    }

}
