package co.jp.nej.earth.processservice;

import org.junit.Test;
import org.quartz.SchedulerException;
import org.springframework.beans.factory.annotation.Autowired;

import co.jp.nej.earth.BaseTest;
import co.jp.nej.earth.batch.AgentBatch;

public class WorkItemDataUpdateProcessServiceTest extends BaseTest {

    @Autowired
    private AgentBatch agentBatch;

    @Test
    public void excute() {
        /*try {
            agentBatch.createJob("co.jp.nej.earth.processservice.WorkItemDataUpdateProcessService", "0/10 * * * * ?",
                    "co.jp.nej.earth.processservice.WorkItemDataUpdateProcessService",
                    "co.jp.nej.earth.processservice.WorkItemDataUpdateProcessService");
        } catch (ClassNotFoundException | SchedulerException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }*/
    }
}
