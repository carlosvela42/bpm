package co.jp.nej.earth.service;

import co.jp.nej.earth.BaseTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

public class EventControlRestServiceTest extends BaseTest {
    @Autowired
    EventControlService eventControlService;

    @Test
    public void unlockEventControl() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\eventList.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(eventControlService.unlockEventControl(workspaceId, "1"), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void unlockEventControl2() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\eventList.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(eventControlService.unlockEventControl(workspaceId, "2"), true);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void unlockEventControl3() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\eventList.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(eventControlService.unlockEventControl(workspaceId, "3"), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }

    @Test
    public void unlockEventControl4() throws Exception {
        String workspaceId = "003";
        File fileInput = new File("src\\test\\resources\\eventList.xml");
        this.excuteTest(workspaceId, fileInput, () -> {
            try {
                Assert.assertEquals(eventControlService.unlockEventControl(workspaceId, "4"), false);
            } catch (Exception e) {
                throw e;
            }
        });
    }
}
