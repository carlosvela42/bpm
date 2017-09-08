//package co.jp.nej.earth.web.controller;
//
//import org.junit.runner.RunWith;
//import org.mockito.runners.MockitoJUnitRunner;
//
//@RunWith(MockitoJUnitRunner.class)
//public class ProcessControllerTest {

//    @InjectMocks
//    private ProcessController controller = new ProcessController();;
//    @Mock
//    private SiteService siteService;
//
//    @Test
//    public void addNew() throws EarthException {
//        Set<Integer> result = new HashSet<>();
//        Mockito.when(siteService.getAllSiteIds(Constant.EARTH_WORKSPACE_ID)).thenReturn(result);
//        Assert.assertEquals(controller.addNew(Constant.EARTH_WORKSPACE_ID), result);
//    }

//    @Test
//    public void deleteList() throws EarthException {
//        DeleteProcessForm form = new DeleteProcessForm();
//        form.setConfirmDelte(true);
//        form.setProcessIds(new ArrayList<>());
//        form.setWorkspaceId(Constant.EARTH_WORKSPACE_ID);
//        Map<String, Object> reuslt = new HashMap<>();
//        Mockito.when(controller.deleteList(form)).thenReturn(reuslt);
//        Assert.assertEquals(controller.deleteList(form), reuslt);
//    }

// @Test
// public void deleteList2() throws EarthException {
// DeleteProcessForm form = new DeleteProcessForm();
// form.setConfirmDelte(false);
// form.setWorkspaceId(Constant.EARTH_WORKSPACE_ID);
// System.out.println(controller.deleteList(form).get("message"));
// }
//
// @Test
// public void deleteList3() throws EarthException {
// DeleteProcessForm form = new DeleteProcessForm();
// List<Integer> processIds = new ArrayList<>();
// processIds.form.setConfirmDelte(true);
// form.setProcessIds(new ArrayList<>());
// form.setWorkspaceId(Constant.EARTH_WORKSPACE_ID);
// System.out.println(controller.deleteList(form).get("message"));
// }
//}
