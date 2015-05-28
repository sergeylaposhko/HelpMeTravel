package ua.laposhko.hmt.session.redis;

import java.util.Random;

import redis.clients.jedis.Jedis;
import ua.laposhko.hmt.entity.User;
import ua.laposhko.hmt.session.SessionManager;

/**
 * @author Sergey Laposhko
 */
public class RedisSessionManager extends SessionManager {

    private static final String REDIS = "localhost";

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.session.SessionManager#sessionExsists(java.lang.String)
     */
    @Override
    public boolean sessionExsists(String sessionCode) {
        Jedis jedis = new Jedis(REDIS);
        boolean res = jedis.exists(sessionCode);
        jedis.close();
        return res;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.session.SessionManager#createSession(java.lang.String)
     */
    @Override
    public String createSession(String userId) {
        Random random = new Random();
        String firstPartString = null;
        firstPartString = String.valueOf(userId.hashCode());
        String key = firstPartString + random.nextLong();
        Jedis jedis = new Jedis(REDIS);
        while (jedis.exists(key)) {
            key = firstPartString + random.nextLong();
            System.out.println(firstPartString);
            System.out.println(key);
        }
        jedis.set(key, userId);
        jedis.close();
        return key;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * ua.laposhko.hmt.session.SessionManager#closeSession(java.lang.String)
     */
    @Override
    public void closeSession(String sessionCode) {
        Jedis jedis = new Jedis(REDIS);
        jedis.del(sessionCode);
        jedis.close();
    }

    /*
     * (non-Javadoc)
     * 
     * @see ua.laposhko.hmt.session.SessionManager#getUserId(java.lang.String)
     */
    @Override
    public Long getUserId(String sessionId) {
        if (!sessionExsists(sessionId)) {
            return null;
        }
        Jedis jedis = new Jedis(REDIS);
        String res = jedis.get(sessionId);
        jedis.close();
        return Long.valueOf(res);
    }

}
