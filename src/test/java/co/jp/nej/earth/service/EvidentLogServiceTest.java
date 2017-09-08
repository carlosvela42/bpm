//package co.jp.nej.earth.service;
//
//import co.jp.nej.earth.BaseTest;
////import co.jp.nej.earth.dao.StrLogAccessDao;
//import co.jp.nej.earth.model.entity.StrLogAccess;
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
///**
// * Test EvidentLog service
// */
//public class EvidentLogServiceTest extends BaseTest {
//
//    private static final Logger LOG = LoggerFactory.getLogger(EvidentLogServiceTest.class);
//
//    @Autowired
//    private EvidentLogService evidentLogService;
//
////    @Autowired
////    private StrLogAccessDao strLogAccessDao;
//
//    private String workspaceId;
//
//    @Before
//    public void before() {
//        workspaceId = "001";
//    }
//
//    @Test
//    public void getListByWorkspaceIdTest() {
//        List<StrLogAccess> strLogAccesses = null;
//        try {
////            strLogAccesses = strLogAccessDao.getListByWorkspaceId(workspaceId);
//            strLogAccesses = evidentLogService.getListByWorkspaceId(workspaceId,null,null,null);
//            LOG.info("EvidentLog record : " + Integer.toString(strLogAccesses.size()));
//            Assert.assertTrue(strLogAccesses.size() > 0);
//        } catch (Exception ex) {
//            LOG.error(ex.getMessage());
//            Assert.assertFalse(strLogAccesses == null);
//        }
//    }
//}
