package ua.laposhko.hmt.session;

import org.apache.log4j.Logger;

/**
 * Created by Sergey on 30.05.2015.
 */
public class AlwaysTrueSessionManager extends SessionManager {

    private static final Logger LOGGER = Logger.getLogger(AlwaysTrueSessionManager.class);

    @Override
    public boolean sessionExists(String sessionCode) {
        LOGGER.debug("Checkit for creating session.");
        return true;
    }

    @Override
    public String createSession(Long userId) {
        LOGGER.debug("Creating session with id: " + "sessiongId");
        return "sessionId";
    }

    @Override
    public void closeSession(String sessionCode) {
        LOGGER.debug("Closing session with id: " + sessionCode);
    }

    @Override
    public Long getUserId(String sessionId) {
        LOGGER.debug("Getting user by session " + sessionId);
        return 6L;
    }
}
