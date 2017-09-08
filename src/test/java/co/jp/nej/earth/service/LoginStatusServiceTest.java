package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.util.DateUtil;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by p-dcv-minhtv on 5/8/2017.
 */
public class LoginStatusServiceTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoginStatusServiceTest.class);
    public static final int RECORD = 6;
    public static final String USERID = "USER_MINHTV_";

    //var
    private List<CtlLogin> ctlLogins = new ArrayList<CtlLogin>();
    private List<String> sessionIds = new ArrayList<String>();

    @Autowired
    private LoginStatusService loginStatusService;

    @Autowired
    private PreparingDataService preparingDataService;

    @Autowired
    private HttpSession httpSession;

    @Before
    public void before() throws EarthException {
        LOG.info("Before: Insert CtlLogin");
        CtlLogin ctlLogin;
        for (int i = 0; i < RECORD; i++) {
            String userId = USERID + i;
            String sessionId = httpSession.getId() + i;
            String loginTime = DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD);
            String logoutTime = DateUtil.getCurrentDate(Constant.DatePattern.DATE_FORMAT_YYYY_MM_DD);
            ctlLogin = new CtlLogin(sessionId, userId, loginTime, logoutTime);
            preparingDataService.insertOneCtlLogin(ctlLogin);
            ctlLogins.add(ctlLogin);
            sessionIds.add(sessionId);
        }
        LOG.info("After: Insert CtlLogin");
    }

    @After
    public void after() throws EarthException {
        LOG.info("Before: Delete CtlLogin" + RECORD);
        LOG.info("After Delete CtlLogin:" + loginStatusService.deleteList(sessionIds));
    }

    @Test
    public void getAll() throws EarthException {
        List<CtlLogin> ctlLogins = null;
        ctlLogins = loginStatusService.getAll(null, null, null);
        LOG.info("Get All user : " + ctlLogins.size() + " records");
        Assert.assertTrue(ctlLogins.size() >= RECORD);
    }
}
