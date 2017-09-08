package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.UserInfo;
import co.jp.nej.earth.model.constant.Constant.MessageCodeLogin;
import co.jp.nej.earth.model.constant.Constant.Session;
import co.jp.nej.earth.util.LoginUtil;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.List;

public class UserServiceTest1 extends BaseTest {
    @Autowired
    private UserService userService;

    /*
     * private HttpSession session;

     * @Before public void setup() { HttpServletRequest request =
     * Mockito.mock(HttpServletRequest.class); MockHttpSession mockHttpSession =
     * new MockHttpSession();
     * Mockito.when(request.getSession()).thenReturn(mockHttpSession); session =
     * request.getSession(); UserInfo userInfo = new UserInfo();
     * userInfo.setUserId("admin"); userInfo.setUserName("Administrator");
     * userInfo.setLoginToken("YWRtaW58MTQ5MjEzNjQ3MDQ0NQ==");
     * session.setAttribute(Session.USER_INFO, userInfo); }
     */

    @Autowired
    private HttpSession session;

    @Before
    public void setup() {
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId("admin");
        userInfo.setUserName("Administrator");
        userInfo.setLoginToken("YWRtaW58MTQ5MjEzNjQ3MDQ0NQ==");
        session.setAttribute(Session.USER_INFO, userInfo);
    }

//    @Test
//    public void testLogin2() throws EarthException {
//        List<Message> listMessage = userService.login("", "123456", session, 0);
//        Assert.assertTrue(listMessage != null && listMessage.size() > 0
//                && listMessage.get(0).getCode().equals(MessageCodeLogin.USR_BLANK));
//    }
//
//    @Test
//    public void testLogin3() throws EarthException {
//        List<Message> listMessage = userService.login("admin", "", session, 0);
//        Assert.assertTrue(listMessage != null && listMessage.size() > 0
//                && listMessage.get(0).getCode().equals(MessageCodeLogin.PWD_BLANK));
//    }
//
//    @Test
//    public void testLogin4() throws EarthException {
//        List<Message> listMessage = userService.login("", "", session, 0);
//        Assert.assertTrue(listMessage != null && listMessage.size() == 2);
//    }
//
//    @Test
//    public void testLogin5() throws EarthException {
//        List<Message> listMessage = userService.login("admin", "123457", session, 0);
//        Assert.assertTrue(listMessage != null && listMessage.size() > 0
//                && listMessage.get(0).getCode().equals(MessageCodeLogin.INVALID_LOGIN));
//    }
//
//    @Test
//    public void testLogin1() throws EarthException {
//        List<Message> listMessage = userService.login("admin", "123456", session, 0);
//        Assert.assertFalse(listMessage != null && listMessage.size() > 0);
//    }

    @Test
    public void testLogout() throws EarthException {
        Assert.assertTrue(userService.logout(session));
    }

    @Test
    public void testCheckAuthen() throws EarthException {
        Assert.assertTrue(LoginUtil.checkAuthen(session));
    }
}
