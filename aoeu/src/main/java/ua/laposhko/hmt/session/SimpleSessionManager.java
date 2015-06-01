package ua.laposhko.hmt.session;

import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sergey on 30.05.2015.
 */
public class SimpleSessionManager extends SessionManager {

    private static Map<String, Long> sessions = new HashMap<>();

    private static final Logger LOGGER = Logger.getLogger(SimpleSessionManager.class);

    @Override
    public boolean sessionExists(String sessionCode) {
        LOGGER.debug("Checking session for existence with session is: " + sessionCode);
        return sessions.containsKey(sessionCode);
    }

    @Override
    public String createSession(Long userId) {
        LOGGER.debug("Creating session: " + userId);
        String possibleId = getRandomString();
        while (sessions.containsKey(possibleId)) {
            possibleId = getRandomString();
        }
        sessions.put(possibleId, userId);
        LOGGER.debug("Sessiong is created with id: " + possibleId);
        return possibleId;
    }

    @Override
    public void closeSession(String sessionCode) {
        LOGGER.debug("Closing session with id " + sessionCode);
        sessions.remove(sessionCode);
    }

    @Override
    public Long getUserId(String sessionId) {
        LOGGER.debug("Getting user id for session code: " + sessionId);
        Long userId = sessions.get(sessionId);
        LOGGER.debug("Resolved user id is: " + userId);
        return userId;
    }

    private static String getRandomString() {
        return String.valueOf(new Random().nextInt());
    }

}
