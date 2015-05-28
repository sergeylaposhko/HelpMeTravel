package ua.laposhko.hmt.session;

import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.session.redis.RedisSessionManager;

/**
 * @author Sergey Laposhko
 */
public abstract class SessionManager {

    private static SessionManager instance;

    /**
     * Checks if the current session is opened.
     *
     * @param sessionCode the session code
     * @return true, if successful
     */
    public abstract boolean sessionExsists(String sessionCode);

    /**
     * Creates the session by the userId param.
     *
     * @param userId the user id
     * @return the key for the new session
     */
    public abstract String createSession(String userId);

    /**
     * Closes session by its key.
     *
     * @param sessionCode the session code
     */
    public abstract void closeSession(String sessionCode);

    /**
     * Gets the user id.
     *
     * @param sessionId the users session
     * @return the user id
     */
    public abstract Long getUserId(String sessionId);


    public synchronized static final SessionManager getInstance() {
        if (instance == null) {
            instance = new RedisSessionManager();
        }
        return instance;
    }

}
