package co.jp.nej.earth.batch;

import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.util.ApplicationContextUtil;

public class ProcessorHook extends Thread {

    protected static final Logger LOG = LoggerFactory.getLogger(AgentBatch.class);

    private String userId;
    private String token ="";


    ProcessorHook(String userId, String token) {
        this.userId = userId;
        this.token = token;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public void run(){

        try {
            Scheduler sch = new StdSchedulerFactory().getScheduler();
            if(!sch.isShutdown()) {
                sch.shutdown(true);
                LOG.info("Shutdown the schedule");
            } else {
                LOG.info("The schedule's already shutdown");
            }

            ApplicationContext context = ApplicationContextUtil.getApplicationContext();
            UserService userService = context.getBean(UserService.class);
            LOG.info("token:" + token);
            userService.logoutBatch(userId, token);

        } catch (Exception e) {

            LOG.error(e.getMessage(),e);
        }

    }
}