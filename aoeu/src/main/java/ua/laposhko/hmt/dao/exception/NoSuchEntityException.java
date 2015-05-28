package ua.laposhko.hmt.dao.exception;

/**
 * @author Sergey Laposhko
 */
public class NoSuchEntityException extends Exception {

    private static final long serialVersionUID = -5832257945616677718L;

    private String missingEntityName;

    public NoSuchEntityException(String entityName) {
        missingEntityName = entityName;
    }

    /* (non-Javadoc)
     * @see java.lang.Throwable#getMessage()
     */
    @Override
    public String getMessage() {
        return "There is no matching entity " + missingEntityName;
    }

}
