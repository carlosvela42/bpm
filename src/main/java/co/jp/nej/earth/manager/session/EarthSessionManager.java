package co.jp.nej.earth.manager.session;

import co.jp.nej.earth.dao.CustomTaskDaoImpl;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.service.UserServiceImpl;
import co.jp.nej.earth.util.EStringUtil;
import co.jp.nej.earth.util.LoginUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;
import java.util.Map;

public class EarthSessionManager implements HttpSessionListener {
    private static final Logger LOG = LoggerFactory.getLogger(CustomTaskDaoImpl.class);

    private static final Map<String, HttpSession> earthSessions = new HashMap<>();

    @Autowired
    private UserServiceImpl userService;

    public void sessionCreated(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.Session.USER_INFO);
        if (userInfo != null) {
            String token = userInfo.getLoginToken();
            if (!EStringUtil.isEmpty(token)) {
                earthSessions.put(token, session);
            }
        }
    }

    public void sessionDestroyed(HttpSessionEvent event) {
        HttpSession session = event.getSession();
        UserInfo userInfo = (UserInfo) session.getAttribute(Constant.Session.USER_INFO);
        if (userInfo != null && (EStringUtil.isNotEmpty(userInfo.getLoginToken()))) {
            earthSessions.remove(userInfo.getLoginToken());
        }

        if (LoginUtil.isLogin(session)) {
            try {
                userService.logout(session);
            } catch (EarthException e) {
                LOG.error(e.getMessage(), e);
            }
        }
    }

    public static HttpSession find(String token) {
        return earthSessions.get(token);
    }

    public static void save(String token, HttpSession session) {
        if (!EStringUtil.isEmpty(token)) {
            earthSessions.put(token, session);
        }
    }
}
