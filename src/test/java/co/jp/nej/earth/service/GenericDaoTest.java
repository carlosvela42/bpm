package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.entity.CtlLogin;
import co.jp.nej.earth.model.sql.QCtlLogin;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Predicate;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Test generic DAO.
 *
 * @author landd
 */
public class GenericDaoTest extends BaseTest {

    private static final Logger LOG = LoggerFactory.getLogger(GenericDaoTest.class);

    private static final QCtlLogin qObject = QCtlLogin.newInstance();

    private static final String SESSIONID1 = "GenericDaoTest1";
    private static final String SESSIONID2 = "GenericDaoTest2";
    private static final String SESSIONID3 = "GenericDaoTest3";
    private static final String UPDATELVAUE = "UP";

    private static final int NO_OF_TEST_DATA = 2;

    @Autowired
    private UserService userService;

    @Before
    public void prepareData() throws EarthException {

        LOG.info("prepareData");

        CtlLogin login = new CtlLogin(SESSIONID1, "12", "13", null);
        userService.addCtlLogin(login);

        login = new CtlLogin(SESSIONID2, "22", "23", null);
        userService.addCtlLogin(login);
    }

    @After
    public void truncateData() throws EarthException {

        LOG.info("cleanData");
        List<Map<Path<?>, Object>> conditions = new ArrayList<>();

        Map<Path<?>, Object> condition1 = new HashMap<>();
        condition1.put(qObject.sessionId, SESSIONID1);

        Map<Path<?>, Object> condition2 = new HashMap<>();
        condition2.put(qObject.sessionId, SESSIONID2);

        Map<Path<?>, Object> condition3 = new HashMap<>();
        condition2.put(qObject.sessionId, SESSIONID3);

        conditions.add(condition1);
        conditions.add(condition2);
        conditions.add(condition3);

        userService.deleteCtlLogins(conditions);
    }

    @Test
    public void testGenericFindOne() throws EarthException {

        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qObject.sessionId, SESSIONID1);
        // condition.put(qObject.testLong, 99);
        CtlLogin ctlLogin = userService.getCtlLoginDetail(condition);
        Assert.assertTrue(ctlLogin != null);
    }

    @Test
    public void testGenericDeleteOne() throws EarthException {

        Map<Path<?>, Object> condition = new HashMap<>();
        condition.put(qObject.sessionId, SESSIONID1);

        long result = userService.deleteCtlLogin(condition);
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testGenericDeleteList() throws EarthException {

        List<Map<Path<?>, Object>> conditions = new ArrayList<>();

        Map<Path<?>, Object> condition1 = new HashMap<>();
        condition1.put(qObject.sessionId, SESSIONID1);

        Map<Path<?>, Object> condition2 = new HashMap<>();
        condition2.put(qObject.sessionId, SESSIONID2);

        Map<Path<?>, Object> condition3 = new HashMap<>();
        condition3.put(qObject.sessionId, SESSIONID3);
        userService.deleteCtlLogin(condition3);

        conditions.add(condition1);
        conditions.add(condition2);
        conditions.add(condition3);

        long result = userService.deleteCtlLogins(conditions);

        Assert.assertTrue(result > 0);
    }

    @Test
    public void testGenericDeleteAll() throws EarthException {

        long result = userService.deleteAllCtlLogins();
        Assert.assertTrue(result > 0);
    }

    @Test
    public void testGenericAdd() throws EarthException {

        CtlLogin login = new CtlLogin(SESSIONID3, "32", "33", null);

        long result = userService.addCtlLogin(login);

        Assert.assertTrue(result == 1);
    }

    @Test
    public void testGenericUpdate() throws EarthException {

        Map<Path<?>, Object> condition = new HashMap<>();

        condition.put(qObject.sessionId, SESSIONID1);

        Map<Path<?>, Object> valueMap = new HashMap<>();

        valueMap.put(qObject.userId, "UP");

        // valueMap.put(qObject.testLong, 777);

        // valueMap.put(qObject.testDate, new Date());

        userService.updateCtlLogin(condition, valueMap);

        CtlLogin login = userService.getCtlLoginDetail(condition);

        Assert.assertTrue(UPDATELVAUE.equals(login.getUserId()));
    }
}
