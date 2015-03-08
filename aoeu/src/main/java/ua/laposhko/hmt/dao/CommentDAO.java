package ua.laposhko.hmt.dao;

import java.util.List;

import ua.laposhko.hmt.dao.exception.NoSuchEntityException;
import ua.laposhko.hmt.entity.Comment;

/**
 * The Class CommentDAO.
 *
 * @author Sergey Laposhko
 */
public abstract class CommentDAO {

    /**
     * Find comments by place.
     *
     * @param placeId
     *            the place id
     * @return the list
     */
    public abstract List<Comment> findCommentsByPlace(long placeId);

    /**
     * Creates the comment.
     *
     * @param comment
     *            the comment
     */
    public abstract void createComment(Comment comment)
	    throws NoSuchEntityException;

    /**
     * Delete comment.
     *
     * @param comment
     *            with the id of the comment we want to delete
     */
    public abstract void deleteComment(Comment comment);

    /**
     * Update comment.
     *
     * @param comment
     *            with the old id and new info
     */
    public abstract void updateComment(Comment comment)
	    throws NoSuchEntityException;

}
