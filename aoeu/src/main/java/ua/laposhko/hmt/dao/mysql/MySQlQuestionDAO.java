package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.laposhko.hmt.dao.QuestionDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Question;

/**
 * @author Sergey Laposhko
 *
 */
public class MySQlQuestionDAO extends QuestionDAO {
    
    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#findAllQuestions(long)
     */
    @Override
    public List<Question> findAllQuestions() {
	return MySQLDAOFactory.findlAllEntities(Question.class);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#findQuestionsByCity(long)
     */
    @Override
    public List<Question> findQuestionsByCity(long cityId) {
	List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
	expressions.add(Restrictions.eq("cityId", cityId));
	return MySQLDAOFactory.fintEntitiesByRestrictions(Question.class, expressions);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#findQuestionById(long)
     */
    @Override
    public Question findQuestionById(long id) {
	Question question = (Question) MySQLDAOFactory.findEntityById(Question.class, id);
	return question;
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#createQuestion(ua.laposhko.hmt.entity.Question)
     */
    @Override
    public void createQuestion(Question question) throws NoSuchEntityException {
	MySQLDAOFactory.saveObject(question);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#updateQuestion(ua.laposhko.hmt.entity.Question)
     */
    @Override
    public void updateQuestion(Question question) throws NoSuchEntityException {
	MySQLDAOFactory.updateObject(question);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.QuestionDAO#deleteQuestion(ua.laposhko.hmt.entity.Question)
     */
    @Override
    public void deleteQuestion(Question question) {
	MySQLDAOFactory.deleteObject(question);
    }

}
