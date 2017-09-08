package co.jp.nej.earth.util;

import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.model.entity.MgrUser;
import co.jp.nej.earth.model.enums.Channel;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpSession;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Base64;

/**
 * @author p-tvo-thuynd
 */
public class LoginUtil {

    private static final int USER_MAX_LENGTH = 30;

    public static boolean isLogin(HttpSession session) {
        return ((session != null) && (session.getAttribute(Session.USER_INFO) != null));
    }

    /**
     * generate token based on userId and current date and time.
     *
     * @param session   time when user logs in
     * @return token.
     */
    public static String generateToken(String userId, HttpSession session) throws EarthException {
        return createToken(userId, session.getId(), Channel.INTERNAL);
    }

    /**
     * generate token based on userId and current date and time.
     *
     * @param processId  Process ID
     * @return login batch token.
     */
    public static String generateBatchToken(String userId, String processId) throws EarthException {
        return createToken(userId, processId, Channel.BATCH);
    }


    /**
     * Get host
     *
     * @return Host address
     * @throws EarthException Common Exception
     */
    public static String getHost() throws EarthException {
        try {
            String[] ipArr = InetAddress.getLocalHost().getHostAddress().split("\\.");
            StringBuilder builder = new StringBuilder();
            for (String str : ipArr) {
                builder.append(String.format("%03d", Integer.parseInt(str)));
            }
            return builder.toString();
        } catch (UnknownHostException e) {
            throw new EarthException(e);
        }
    }


    /**
     * check whether user logs in or not.
     *
     * @param session HttpSession object.
     * @return boolean value.
     */
    public static boolean checkAuthen(HttpSession session) {
        return session.getAttribute(Session.USER_INFO) != null;
    }

    /**
     * check whether user exists in db or not.
     *
     * @return boolean value.
     * @throws EarthException
     */
    public static boolean isUserExisted(MgrUser mgrUser, String password) throws EarthException {
        try {
            if (mgrUser == null) {
                return false;
            }

            if (EStringUtil.isEmpty(mgrUser.getPassword())) {
                return EStringUtil.isEmpty(password);
            }

            return (mgrUser.getPassword().equals(password));
        } catch (Exception e) {
            throw new EarthException(e);
        }
    }

    /**
     * Create token key
     *
     * @param userId User ID
     * @param keyId Session ID (WEB) or process ID (BATCH)
     * @param channel Enum Channel
     * @return Token key
     * @throws EarthException Common exception
     */
    private static String createToken(String userId, String keyId, Channel channel) throws EarthException {
        String ipServer = getHost();

        String token;
        if (Channel.BATCH == channel) {
            token = Constant.Token.BATCH_TOKEN;
        } else {
            token = Constant.Token.WEB_TOKEN;
        }

        token += DateUtil.getCurrentDateString()
            + ipServer
            + StringUtils.rightPad(userId, USER_MAX_LENGTH, " ")
            + keyId;
        return new String(Base64.getEncoder().encode(token.getBytes()));
    }
}
