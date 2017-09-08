//package co.jp.nej.earth.service;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.FixMethodOrder;
//import org.junit.Test;
//import org.junit.runners.MethodSorters;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import co.jp.nej.earth.BaseTest;
//import co.jp.nej.earth.exception.EarthException;
//import co.jp.nej.earth.model.MenuAccessRight;
//import co.jp.nej.earth.model.TemplateAccessRight;
//import co.jp.nej.earth.model.TemplateKey;
//import co.jp.nej.earth.model.constant.Constant.DatePattern;
//import co.jp.nej.earth.model.entity.CtlTemplate;
//import co.jp.nej.earth.model.entity.MgrTemplate;
//import co.jp.nej.earth.util.DateUtil;
//
//@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//public class TemplateServiceTest extends BaseTest {
//
//    private static boolean begin = false;
//    private static boolean end = false;
//
//    public static final int THREE = 3;
//    public static final int FOUR = 4;
//    public static final int FIVE = 5;
//
//    @Autowired
//    private PreparingDataService data;
//
//    @Autowired
//    private TemplateService templateService;
//
//    @Before
//    public void setUp() {
//        try {
//            if (begin) {
//                return;
//            }
//
//            data.deleteAllCtlTemplate();
//            data.deleteAllMgrTemplate();
//
//            CtlTemplate ctlTemplate1 = new CtlTemplate();
//            ctlTemplate1.setUserId("admin");
//            ctlTemplate1.setTemplateId("1");
//            ctlTemplate1.setAccessAuthority(THREE);
//            data.insertCtlTemplate("001", ctlTemplate1);
//
//            CtlTemplate ctlTemplate2 = new CtlTemplate();
//            ctlTemplate2.setUserId("admin");
//            ctlTemplate2.setTemplateId("2");
//            ctlTemplate2.setAccessAuthority(FIVE);
//            data.insertCtlTemplate("002", ctlTemplate2);
//
//            CtlTemplate ctlTemplate3 = new CtlTemplate();
//            ctlTemplate3.setUserId("admin");
//            ctlTemplate3.setTemplateId("3");
//            ctlTemplate3.setAccessAuthority(FOUR);
//            data.insertCtlTemplate("003", ctlTemplate3);
//
//            MgrTemplate mgrTemplate1 = new MgrTemplate();
//            mgrTemplate1.setTemplateId("1");
//            mgrTemplate1.setTemplateName("template name 1");
//            mgrTemplate1.setTemplateTableName("TMP_1");
//            mgrTemplate1.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate1.setTemplateType("WORKITEM");
//            mgrTemplate1.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("001", mgrTemplate1);
//
//            MgrTemplate mgrTemplate2 = new MgrTemplate();
//            mgrTemplate2.setTemplateId("2");
//            mgrTemplate2.setTemplateName("template name 2");
//            mgrTemplate2.setTemplateTableName("TMP_2");
//            mgrTemplate2.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate2.setTemplateType("PROCESS");
//            mgrTemplate2.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("001", mgrTemplate2);
//
//            MgrTemplate mgrTemplate3 = new MgrTemplate();
//            mgrTemplate3.setTemplateId("3");
//            mgrTemplate3.setTemplateName("template name 3");
//            mgrTemplate3.setTemplateTableName("TMP_3");
//            mgrTemplate3.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate3.setTemplateType("WORKITEM");
//            mgrTemplate3.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("001", mgrTemplate3);
//
//            MgrTemplate mgrTemplate4 = new MgrTemplate();
//            mgrTemplate4.setTemplateId("4");
//            mgrTemplate4.setTemplateName("template name 4");
//            mgrTemplate4.setTemplateTableName("TMP_4");
//            mgrTemplate4.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate4.setTemplateType("WORKITEM");
//            mgrTemplate4.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("002", mgrTemplate4);
//
//            MgrTemplate mgrTemplate5 = new MgrTemplate();
//            mgrTemplate5.setTemplateId("5");
//            mgrTemplate5.setTemplateName("template name 5");
//            mgrTemplate5.setTemplateTableName("TMP_5");
//            mgrTemplate5.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate5.setTemplateType("PROCESS");
//            mgrTemplate5.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("002", mgrTemplate5);
//
//            MgrTemplate mgrTemplate6 = new MgrTemplate();
//            mgrTemplate6.setTemplateId("6");
//            mgrTemplate6.setTemplateName("template name 6");
//            mgrTemplate6.setTemplateTableName("TMP_6");
//            mgrTemplate6.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate6.setTemplateType("WORKITEM");
//            mgrTemplate6.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("002", mgrTemplate6);
//
//            MgrTemplate mgrTemplate7 = new MgrTemplate();
//            mgrTemplate7.setTemplateId("7");
//            mgrTemplate7.setTemplateName("template name 7");
//            mgrTemplate7.setTemplateTableName("TMP_7");
//            mgrTemplate7.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate7.setTemplateType("WORKITEM");
//            mgrTemplate7.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("003", mgrTemplate7);
//
//            MgrTemplate mgrTemplate8 = new MgrTemplate();
//            mgrTemplate8.setTemplateId("8");
//            mgrTemplate8.setTemplateName("template name 8");
//            mgrTemplate8.setTemplateTableName("TMP_8");
//            mgrTemplate8.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//                  + "{'name':'age','description':'age of User','type':5,'size':3,'required':true,'encrypted':true}]");
//            mgrTemplate8.setTemplateType("PROCESS");
//            mgrTemplate8.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("003", mgrTemplate8);
//
//            MgrTemplate mgrTemplate9 = new MgrTemplate();
//            mgrTemplate9.setTemplateId("9");
//            mgrTemplate9.setTemplateName("template name 9");
//            mgrTemplate9.setTemplateTableName("TMP_9");
//            mgrTemplate9.setTemplateField(
//                  "[{'name':'name','description':'name of User','type':2,'size':15,'required':true,'encrypted':true},"
//            mgrTemplate9.setTemplateType("WORKITEM");
//            mgrTemplate9.setLastUpdateTime(DateUtil.getCurrentDate(DatePattern.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
//            data.insertMgrTemplate("003", mgrTemplate9);
//
//            begin = true;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Test
//    public void testGetMixAuthorityTemplate() throws EarthException {
//        List<String> listWorkspaceId = new ArrayList<String>();
//        listWorkspaceId.add("001");
//        listWorkspaceId.add("002");
//        listWorkspaceId.add("003");
//        Map<TemplateKey, TemplateAccessRight> map = new HashMap<TemplateKey, TemplateAccessRight>();
//        for (String s : listWorkspaceId) {
//            map.putAll(data.getMixAuthorityTemplate("admin", s));
//        }
//        Assert.assertEquals(THREE, map.size());
//    }
//
//    @Test
//    public void testGetMixAuthorityMenu() throws EarthException {
//        Map<String, MenuAccessRight> map = data.getMixAuthorityMenu("admin");
//        Assert.assertTrue(map.size() == 2);
//    }
//
//    @Test
//    public void testGetAllByWorkspace() throws EarthException {
//        Assert.assertEquals(THREE, templateService.getAllByWorkspace("001").size());
//    }
//
//    @Test
//    public void testXDelete() throws EarthException {
//        if (end) {
//            return;
//        }
//        data.deleteAllCtlTemplate();
//        data.deleteAllMgrTemplate();
//        end = true;
//    }
//}
