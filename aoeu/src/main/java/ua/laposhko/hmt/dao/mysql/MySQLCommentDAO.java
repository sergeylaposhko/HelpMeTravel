package ua.laposhko.hmt.dao.mysql;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

import ua.laposhko.hmt.dao.CommentDAO;
import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Comment;

/**
 * @author Sergey Laposhko
 */
public class MySQLCommentDAO extends CommentDAO {

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CommentDAO#findCommentsByPlace(long)
     */
    @Override
    public List<Comment> findCommentsByPlace(long placeId) {
        List<SimpleExpression> expressions = new ArrayList<SimpleExpression>();
        expressions.add(Restrictions.eq("placeId", placeId));
        return MySQLDAOFactory.fintEntitiesByRestrictions(Comment.class, expressions);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CommentDAO#createComment(ua.laposhko.hmt.entity.Comment)
     */
    @Override
    public void createComment(Comment comment) throws NoSuchEntityException {
        MySQLDAOFactory.saveObject(comment);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CommentDAO#deleteComment(ua.laposhko.hmt.entity.Comment)
     */
    @Override
    public void deleteComment(Comment comment) {
        MySQLDAOFactory.deleteObject(comment);
    }

    /* (non-Javadoc)
     * @see ua.laposhko.hmt.dao.CommentDAO#updateComment(ua.laposhko.hmt.entity.Comment)
     */
    @Override
    public void updateComment(Comment comment) throws NoSuchEntityException {
        MySQLDAOFactory.updateObject(comment);
    }

}
