package co.jp.nej.earth.processservice;

import co.jp.nej.earth.config.JdbcConfig;
import co.jp.nej.earth.service.ProcessIServiceService;
import co.jp.nej.earth.service.WorkItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.Scope;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

/**
 * Class WorkItemDataUpdateProcessService
 *
 * @author DaoPQ
 */
@Configuration
@PropertySource("classpath:batch_config.properties")
@Import(JdbcConfig.class)
@ComponentScan(basePackages = { "co.jp.nej.earth" }, excludeFilters = {
    @ComponentScan.Filter(type = FilterType.ANNOTATION, value = Configuration.class),
    @ComponentScan.Filter(type = FilterType.REGEX, pattern = "co.jp.nej.earth.web.controller.*") })
public class UpdateProcessDataService {
    private static final Logger LOG = LoggerFactory.getLogger(UpdateProcessDataService.class);

    /**
     * Exec thread
     *
     * @param user
     * @param processId
     * @param maxThread
     * @param threadSleep
     * @param hostName
     */
    public void execute(String user, int processId, int maxThread, int threadSleep, String hostName) {
        try {
            ApplicationContext context = new AnnotationConfigApplicationContext(UpdateProcessDataService.class);
            WorkItemService workItemService = context.getBean(WorkItemService.class);
            ProcessIServiceService processIServiceService = context.getBean(ProcessIServiceService.class);

            ThreadPoolTaskExecutor taskExecutor = taskExecutor(maxThread);

            while (true) {
                ExecuteTaskProcessUpdate task =  initialTask(user, processId, maxThread, threadSleep, hostName,
                    workItemService, processIServiceService);
                taskExecutor.execute(task);
                Thread.sleep(threadSleep);
            }
        } catch (Exception e) {
            execute(user, processId, maxThread, threadSleep, hostName);
        }
    }

    /**
     * Inital task
     *
     * @param user
     * @param processId
     * @param maxThread
     * @param threadSleep
     * @param hostName
     * @param workItemService
     * @param processIServiceService
     * @return
     */
    private ExecuteTaskProcessUpdate initialTask(String user, int processId, int maxThread, int threadSleep,
                                                 String hostName, WorkItemService workItemService,
                                                 ProcessIServiceService processIServiceService) {
        ExecuteTaskProcessUpdate task = new ExecuteTaskProcessUpdate();
        task.setWorkItemService(workItemService);
        task.setProcessIServiceService(processIServiceService);
        task.setUserId(user);
        task.setHostName(hostName);
        task.setProcessServiceID(processId);
        return task;
    }

    /**
     * Create Thread Pool
     *
     * @param maxPoolSize Max pool size
     */
    private ThreadPoolTaskExecutor taskExecutor(int maxPoolSize) {
        ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();
        pool.initialize();
        pool.setMaxPoolSize(maxPoolSize);
        pool.setWaitForTasksToCompleteOnShutdown(true);
        return pool;
    }

    @Component
    @Scope("prototype")
    private class ExecuteTaskProcessUpdate implements Runnable {

        private ProcessIServiceService processIServiceService;
        private WorkItemService workItemService;
        private String userId;
        private Integer processServiceID;
        private String hostName;
        private static final String SESSION_ID_DEFAULT = " ";

        void setWorkItemService(WorkItemService workItemService) {
            this.workItemService = workItemService;
        }

        void setProcessIServiceService(ProcessIServiceService processIServiceService) {
            this.processIServiceService = processIServiceService;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        void setProcessServiceID(Integer processServiceID) {
            this.processServiceID = processServiceID;
        }

        public String getHostName() {
            return hostName;
        }

        public void setHostName(String hostName) {
            this.hostName = hostName;
        }

        public void run() {
            try {
                String workspaceId =
                    String.valueOf(processIServiceService.getWorkSpaceIdByProcessServiceId(processServiceID));
                workItemService.insertOrUpdateWorkItemToDbFromEvent(workspaceId,Thread.currentThread().getId()
                    , hostName, processServiceID, userId, SESSION_ID_DEFAULT);
            } catch (Exception ex) {
                LOG.debug(ex.getMessage());
                ex.printStackTrace();
            }
        }
    }
}
