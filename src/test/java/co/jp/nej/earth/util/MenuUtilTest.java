//package co.jp.nej.earth.util;
//
//import java.util.Map;
//
//import javax.servlet.http.HttpSession;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import co.jp.nej.earth.BaseTest;
//import co.jp.nej.earth.exception.EarthException;
//import co.jp.nej.earth.model.MenuAccessRight;
//import co.jp.nej.earth.model.enums.AccessRight;
//import co.jp.nej.earth.service.PreparingDataService;
//
//public class MenuUtilTest extends BaseTest{
//    @Autowired
//    private PreparingDataService data;
//
//    @Autowired
//    private HttpSession session;
//
//    @Before
//    public void setUp() throws EarthException{
//        Map<String, MenuAccessRight> menuAccessRightMap = data.getMixAuthorityMenu("admin");
//        MenuUtil.saveToSession(session, menuAccessRightMap);
//    }
//
//    @Test
//    public void testetListMenu(){
//        Assert.assertEquals(2, MenuUtil.getListMenu(session).size());
//    }
//
//    @Test
//    public void testGetAuthority(){
//        Assert.assertEquals(AccessRight.values()[0], MenuUtil.getAuthority(session, "functionId_11"));
//    }
//}
