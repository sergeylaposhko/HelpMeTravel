package ua.laposhko.hmt.entity;

import java.sql.Date;

/**
 * @author Sergey Laposhko
 *
 */
public class Answer {

    private long id;
    private long userId;
    private long questionId;
    private String text;
    private Date date;

    /**
     * @param id
     * @param userId
     * @param questionId
     * @param text
     */
    public Answer(long id, long userId, long questionId, String text, Date date) {
	super();
	this.id = id;
	this.userId = userId;
	this.questionId = questionId;
	this.text = text;
	this.date = date;
    }

    /**
     * 
     */
    public Answer() {
    }

    /**
     * @return the id
     */
    public long getId() {
	return id;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(long id) {
	this.id = id;
    }

    /**
     * @return the userId
     */
    public long getUserId() {
	return userId;
    }

    /**
     * @param userId
     *            the userId to set
     */
    public void setUserId(long userId) {
	this.userId = userId;
    }

    /**
     * @return the questionId
     */
    public long getQuestionId() {
	return questionId;
    }

    /**
     * @param questionId
     *            the questionId to set
     */
    public void setQuestionId(long questionId) {
	this.questionId = questionId;
    }

    /**
     * @return the text
     */
    public String getText() {
	return text;
    }

    /**
     * @param text
     *            the text to set
     */
    public void setText(String text) {
	this.text = text;
    }

    /**
     * @return the date
     */
    public Date getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(Date date) {
        this.date = date;
    }
    
}
