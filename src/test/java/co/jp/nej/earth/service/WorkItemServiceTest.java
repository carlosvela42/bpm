package co.jp.nej.earth.service;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.enums.Channel;

public class WorkItemServiceTest extends BaseTest {
    private static final Logger LOG = LoggerFactory.getLogger(WorkItemServiceTest.class);
    @Autowired
    WorkItemService workItemService;
    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;
    
    @Test
    public void getWorkItemStructureTest1() throws Exception {
        if (userService.login("admin", "123456", session, Channel.INTERNAL).size() > 0) {
            LOG.error("Login failed!");
        }

        List<Message> result = workItemService.openWorkItem(session, "35", "1", 1);
        if (result.size() > 0) {
            LOG.error("Login failed!");
        }

//        Assert.assertTrue(result);
        LOG.info("Open Process Result:" + result);
    }
}
