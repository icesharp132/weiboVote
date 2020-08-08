package cn.hui.vote.web.util;

import cn.hui.vote.web.domain.Session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author luzhaohui@didiglobal.com
 * @date 2020/8/5 02:18
 */
public class SessionManager {

    private static Map<String, Session> sessionMap = new ConcurrentHashMap<>();

    private SessionManager() {};

    public static void newSession(String token) {
        sessionMap.put(token, new Session());
    }

    public static Session getSession(String token) {
        return sessionMap.get(token);
    }

    public static Session removeSession(String token) {
        return sessionMap.remove(token);
    }

}
