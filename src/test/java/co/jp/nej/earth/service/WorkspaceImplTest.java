// package co.jp.nej.earth.service;
//
// import java.util.ArrayList;
// import java.util.List;
//
// import org.junit.Assert;
// import org.junit.Before;
// import org.junit.Test;
// import org.springframework.beans.factory.annotation.Autowired;
//
// import co.jp.nej.earth.BaseTest;
// import co.jp.nej.earth.exception.EarthException;
// import co.jp.nej.earth.model.Message;
// import co.jp.nej.earth.model.MgrWorkspace;
// import co.jp.nej.earth.model.MgrWorkspaceConnect;
//
// public class WorkspaceImplTest extends BaseTest{
//
// List<String> workspaceIds = new ArrayList<String>();
// MgrWorkspaceConnect mgrWorkspaceConnect = new MgrWorkspaceConnect();
//
// @Autowired
// WorkspaceService workspaceService;
//
// @Before
// public void before(){
//
// try {
// mgrWorkspaceConnect.setWorkspaceId("001");
// mgrWorkspaceConnect.setSchemaName("schema1");
// mgrWorkspaceConnect.setDbUser("DbUser1");
// mgrWorkspaceConnect.setDbPassword("DbPassword1");
// mgrWorkspaceConnect.setOwner("Owner1");
// mgrWorkspaceConnect.setDbServer("jdbcConnection1");
// mgrWorkspaceConnect.setLastUpdateTime("2017/03/01");
//
// workspaceIds.add(mgrWorkspaceConnect.getWorkspaceId());
//
// } catch (Exception ex) {
// Assert.assertTrue(1 < 0);
// }
// }
//
// @Test
// public void getAllTest() {
// List<MgrWorkspace> mgrWorkspace = new ArrayList<>();
// try {
// mgrWorkspace = workspaceService.getAll();
// Assert.assertTrue(mgrWorkspace != null && mgrWorkspace.size() > 0);
// } catch (EarthException e) {
// System.out.println(e.getMessage());
// Assert.assertFalse(mgrWorkspace == null || mgrWorkspace.size() < 0);
// }
// }
//
// @Test
// public void insertOneTest() {
// List<Message> messages = new ArrayList<>();
// boolean insertMgrWorkspace = false;
// try {
// messages = workspaceService.validateInsert(mgrWorkspaceConnect);
// Assert.assertFalse(messages != null && messages.size() > 0);
//
// insertMgrWorkspace = workspaceService.insertOne(mgrWorkspaceConnect);
// Assert.assertTrue(insertMgrWorkspace);
//
// } catch (Exception ex) {
// System.out.println(ex.getMessage());
// Assert.assertFalse(insertMgrWorkspace);
// }
// }
//
// @Test
// public void getByIdTest() {
// MgrWorkspaceConnect mgrWorkspaceConnectNew = new MgrWorkspaceConnect();
// try {
// mgrWorkspaceConnectNew =
// workspaceService.getDetail(mgrWorkspaceConnect.getWorkspaceId());
// Assert.assertTrue(mgrWorkspaceConnectNew != null);
// Assert.assertEquals(mgrWorkspaceConnectNew, mgrWorkspaceConnect);
// } catch (Exception ex) {
// System.out.println(ex.getMessage());
// Assert.assertFalse(mgrWorkspaceConnectNew == null);
// Assert.assertNotEquals(mgrWorkspaceConnectNew, mgrWorkspaceConnect);
// }
// }
//
//
// @Test
// public void updateOneTest() {
// List<Message> messages = new ArrayList<>();
// boolean updateMgrWorkspace = false;
// try {
// messages = workspaceService.validateUpdate(mgrWorkspaceConnect);
// Assert.assertFalse(messages != null && messages.size() > 0);
//
// updateMgrWorkspace = workspaceService.updateOne(mgrWorkspaceConnect);
// Assert.assertTrue(updateMgrWorkspace);
//
// } catch (Exception ex) {
// System.out.println(ex.getMessage());
// Assert.assertFalse(updateMgrWorkspace);
// }
// }
//
// /* @Test
// public void deleteListTest() {
// boolean deleteUser = false;
// try {
// deleteUser =workspaceService.deleteList(workspaceIds);
// Assert.assertTrue(deleteUser);
// } catch (Exception e) {
// e.printStackTrace();
// Assert.assertFalse(deleteUser);
// }
// }*/
//
// }
