package co.jp.nej.earth.util;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.TemplateAccessRight;
import co.jp.nej.earth.model.TemplateKey;
import co.jp.nej.earth.model.entity.CtlTemplate;
import co.jp.nej.earth.model.enums.AccessRight;
import co.jp.nej.earth.service.PreparingDataService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TemplateUtilTest extends BaseTest {

    public static final int INDEX = 3;
    @Autowired
    private PreparingDataService data;

    @Autowired
    private HttpSession session;

    public static final int THREE = 3;
    public static final int FOUR = 4;
    public static final int FIVE = 5;

    @Before
    public void setUp() throws EarthException {
        data.deleteAllCtlTemplate();

        CtlTemplate ctlTemplate1 = new CtlTemplate();
        ctlTemplate1.setUserId("admin");
        ctlTemplate1.setTemplateId("1");
        ctlTemplate1.setAccessAuthority(THREE);
        data.insertCtlTemplate("001", ctlTemplate1);

        CtlTemplate ctlTemplate2 = new CtlTemplate();
        ctlTemplate2.setUserId("admin");
        ctlTemplate2.setTemplateId("2");
        ctlTemplate2.setAccessAuthority(FIVE);
        data.insertCtlTemplate("002", ctlTemplate2);

        CtlTemplate ctlTemplate3 = new CtlTemplate();
        ctlTemplate3.setUserId("admin");
        ctlTemplate3.setTemplateId("3");
        ctlTemplate3.setAccessAuthority(FOUR);
        data.insertCtlTemplate("003", ctlTemplate3);

        List<String> listWorkspaceId = new ArrayList<String>();
        listWorkspaceId.add("001");
        listWorkspaceId.add("002");
        listWorkspaceId.add("003");
        TemplateAccessRight templateAccessRightMap = new TemplateAccessRight();
        for (String s : listWorkspaceId) {
            templateAccessRightMap.setTemplatesAccessRights(data.getMixAuthorityTemplate("admin", s));
        }
        TemplateUtil.saveToSession(session, templateAccessRightMap);
    }

    @Test
    public void testGetAccessibleTemplates() {
        Assert.assertEquals(1, TemplateUtil.getAccessibleTemplates(session, "001").size());
    }

    @Test
    public void testGetAuthority() {
        TemplateKey templateKey = new TemplateKey();
        templateKey.setTemplateId("1");
        templateKey.setWorkspaceId("001");
        Assert.assertEquals(AccessRight.values()[INDEX], TemplateUtil.getAuthority(session, templateKey));
    }

    @After
    public void tearDown() throws EarthException {
        data.deleteAllCtlTemplate();
    }
}
