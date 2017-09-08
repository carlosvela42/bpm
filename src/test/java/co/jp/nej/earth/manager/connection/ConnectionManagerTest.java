package co.jp.nej.earth.manager.connection;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.service.WorkspaceService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class ConnectionManagerTest extends BaseTest {

    @Autowired
    private WorkspaceService workspaceService;

    @Test
    public void testGetAllConnections1() throws EarthException { //
//        Map<String, EarthQueryFactory> earthQueryFactories = ConnectionManager.getEarthQueryFactory(workspaceId);
//        ConnectionManager.getEarthQueryFactory("002");
//        ConnectionManager.getEarthQueryFactory("003");
        workspaceService.getAllWorkspaceConnections();
//        ConnectionManager.getEarthQueryFactory("004");
//        ConnectionManager.getEarthQueryFactory("005");
//        Assert.assertTrue(earthQueryFactories.size() == 4);
//        Assert.assertFalse(earthQueryFactories.size() == 0);
    }
}
