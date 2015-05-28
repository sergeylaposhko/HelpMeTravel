package ua.laposhko.hmt.dao.mysql.sqlquery;

/**
 * @author Sergey Laposhko
 */
public final class AnswerSQL {

    public static final String ANSWER_ID = "id";
    public static final String ANSWER_USER_ID = "user_id";
    public static final String ANSWER_QUESTION_ID = "question_id";
    public static final String ANSWER_TEXT = "text";

    public static final String SQL_SELECT_FROM_ANSWER_ALL_ANSWERS = "SELECT * from answer";
    public static final String SQL_SELECT_ANSWER_BY_ID = "SELECT * from answer WHERE "
            + ANSWER_ID + " = ? ";
    public static final String SQL_SELECT_ANSWER_BY_QUESTION = "SELECT * from answer WHERE "
            + ANSWER_QUESTION_ID + "  = ? ";

    public static final String SQL_INSERT_ANSWER = "INSERT INTO answer ("
            + ANSWER_ID + ", " + ANSWER_USER_ID + ", " + ANSWER_QUESTION_ID
            + ", " + ANSWER_TEXT + ") VALUES(DEFAULT,?,?,?)";

}
