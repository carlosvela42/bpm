package co.jp.nej.earth.batch;

import co.jp.nej.earth.config.MessageConfig;
import co.jp.nej.earth.config.JdbcConfig;
import co.jp.nej.earth.exception.EarthException;
import co.jp.nej.earth.model.AgentSchedule;
import co.jp.nej.earth.model.Message;
import co.jp.nej.earth.model.MessagesWithToken;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.model.constant.Constant.ErrorCode;
import co.jp.nej.earth.processservice.UpdateProcessDataService;
import co.jp.nej.earth.service.ScheduleService;
import co.jp.nej.earth.service.UserService;
import co.jp.nej.earth.util.ApplicationContextUtil;
import co.jp.nej.earth.util.CryptUtil;
import co.jp.nej.earth.util.DateUtil;
import co.jp.nej.earth.util.EMessageResource;

import co.jp.nej.earth.util.EStringUtil;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.Import;
//import org.springframework.context.annotation.PropertySources;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

//@Configuration
@Import({ JdbcConfig.class, MessageConfig.class })
@ComponentScan(basePackages = { "co.jp.nej.earth" }, excludeFilters = {
        @Filter(type = FilterType.ANNOTATION, value = Configuration.class),
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = "co.jp.nej.earth.web.controller.*") })
@Component
public class AgentBatch {

    /* Constant for parameter */
    private static final int MAX_PARA_NEED = 7;

    private static final int PARA_HOST = 0;
    private static final int PARA_PROCESS_SEVICE_ID = 1;
    private static final int PARA_USER_ID = 2;
    private static final int PARA_PASSWORD = 3;
    private static final int PARA_MAX_THREAD = 4;
    private static final int PARA_THREAD_SLEEP = 5;
    private static final int PARA_IMPORT_FOLDER = 6;
    private static final int PARA_TEMP_FOLDER = 7;

    @Autowired
    private ScheduleService scheduleService;

    // @Autowired
    private static EMessageResource messageSource;

    private AgentInfo agentInfo;

    private Scheduler scheduler;

    protected static final Logger LOG = LoggerFactory.getLogger(AgentBatch.class);

    @SuppressWarnings("unchecked")
    public void createCronJob(String className, String schedule, String jobKey, String triggerKey, int wpID)
            throws SchedulerException, ClassNotFoundException {

        Class<? extends Job> processClass = (Class<? extends Job>) Class.forName(className);

        JobKey jk = new JobKey(jobKey);
        JobDetail job = JobBuilder.newJob(processClass).withIdentity(jk).build();

        TriggerKey tk = new TriggerKey(triggerKey);

        // Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tk)
        // .withSchedule(CronScheduleBuilder.cronSchedule(schedule)).build();

        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(tk)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule()).build();
        Scheduler scheduler = new StdSchedulerFactory().getScheduler();

        scheduler.start();
        scheduler.scheduleJob(job, trigger);
    }

    public void run(int processServiceId) throws ClassNotFoundException, SchedulerException, EarthException {

        // Get list schedule
        List<AgentSchedule> listSchedule = scheduleService.getScheduleByProcessServiceId(processServiceId);
        if (listSchedule != null) {
            if (listSchedule.size() > 0) {
                scheduler = new StdSchedulerFactory().getScheduler();
                // scheduler.getListenerManager().addTriggerListener(new
                // AgentTriggerListener());

                scheduler.getListenerManager().addJobListener(new AgentJobListener());

                //scheduler.start();

                for (AgentSchedule schedule : listSchedule) {
                    try {
                        ScheduleTimeHelper helper = new ScheduleTimeHelper(schedule);

                        String workspaceId = ApplicationContextUtil.getWorkspaceId();
                        String className = schedule.getClassName();
                        String jobId = schedule.getScheduleId();
                        int interval = helper.getTotalSecond();

                        Date startTime = helper.getStartTime();
                        Date endTime = helper.getEndTime();
                        Date nextTime = helper.getNextTime();
                        Date currentTime = DateUtil.getCurrentDate();

                        @SuppressWarnings("unchecked")
                        Class<? extends Job> processClass = (Class<? extends Job>) Class.forName(className);

                        JobKey jobKey = new JobKey(Constant.AgentBatch.PRE_JOB_KEY + jobId);
                        JobDetail job = JobBuilder.newJob(processClass).withIdentity(jobKey).build();
                        job.getJobDataMap().put(Constant.AgentBatch.P_WORKSPACE_ID, workspaceId);
                        job.getJobDataMap().put(Constant.AgentBatch.P_USER_ID, agentInfo.userId);
                        job.getJobDataMap().put(Constant.AgentBatch.P_SCHEDULE_ID, schedule.getScheduleId());
                        job.getJobDataMap().put(Constant.AgentBatch.P_END_TIME, schedule.getEndTime());
                        job.getJobDataMap().put(Constant.AgentBatch.P_INTERVAL_SECOND, interval);
                        job.getJobDataMap().put(Constant.AgentBatch.P_IMPORT_PATH, agentInfo.importPath);
                        job.getJobDataMap().put(Constant.AgentBatch.P_TEMP_PATH, agentInfo.tempPath);

                        TriggerKey triggerKey = new TriggerKey(Constant.AgentBatch.PRE_TRIGER_KEY + jobId);

                        SimpleScheduleBuilder jobSchedule = SimpleScheduleBuilder.simpleSchedule();
                        //jobSchedule.withIntervalInSeconds(interval).withRepeatCount(1);
                        jobSchedule.withIntervalInSeconds(interval).repeatForever();

                        Date triggerStartTime = startTime;

                        // If time is over then run job immediately
                        if (nextTime.after(currentTime)) {
                            if (endTime==null || nextTime.before(endTime)) {
                                triggerStartTime = nextTime;
                            }
                        } else {
                            triggerStartTime = currentTime;
                        }

                        Trigger trigger;
                        if(endTime!=null){
                            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(triggerStartTime)
                                .withSchedule(jobSchedule).endAt(endTime).build();
                        } else {
                            trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).startAt(triggerStartTime)
                                    .withSchedule(jobSchedule).build();
                        }

                        scheduler.scheduleJob(job, trigger);
                        //System.out.println(DateUtil.convertSimpleDateFormat(
                        //        trigger.getNextFireTime(), Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS));

                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                scheduler.start();

            }
        }

    }

    public static void main(String[] args) throws EarthException, ClassNotFoundException, SchedulerException {

        ApplicationContext context = new AnnotationConfigApplicationContext(AgentBatch.class);
        ApplicationContextUtil appUtil = new ApplicationContextUtil();
        appUtil.setApplicationContext(context);

        AgentBatch agentBatch = context.getBean(AgentBatch.class);
        UserService userService = context.getBean(UserService.class);
        messageSource = context.getBean(EMessageResource.class);

        if (args.length > MAX_PARA_NEED) {

            String host = args[PARA_HOST];
            String processServiceId = args[PARA_PROCESS_SEVICE_ID];
            String userId = args[PARA_USER_ID];
            String pass = args[PARA_PASSWORD];
            String maxThread = args[PARA_MAX_THREAD];
            String threadSleep = args[PARA_THREAD_SLEEP];
            String importPath = args[PARA_IMPORT_FOLDER];
            String tempPath = args[PARA_TEMP_FOLDER];

            String token = EStringUtil.EMPTY;

            AgentInfo info = parse(processServiceId, userId, pass, maxThread, threadSleep, importPath, tempPath, host);

            if (info.errors.isEmpty()) {
                agentBatch.agentInfo = info;
                try {
                    int workspaceId = agentBatch.scheduleService.getWorkspaceByProcessServiceId(info.processServiceId);
                    if (workspaceId > 0) {
                        appUtil.setWorkspaceId(Integer.toString(workspaceId));

                        MessagesWithToken messagesWithToken = userService.loginBatch(info.userId, info.password,
                            String.valueOf(info.processServiceId));
                        List<Message> messages = messagesWithToken.getMessages();
                        token = messagesWithToken.getToken();

                        // No error, run batch
                        if (Message.isAllWarningOrEmpty(messages)) {
                            Runtime.getRuntime().addShutdownHook(new ProcessorHook(info.userId, token));
                            // Start schedule
                            agentBatch.run(info.processServiceId);

                            // Start Process Update Service
                            startUpdateProcess(info.userId, info.processServiceId, info.maxThread, info.threadSleep,
                                    info.host);
                        } else {

                            // Log error
                            for (Message message : messages) {
                                LOG.error(message.getContent());
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if(userService != null) {
                        userService.logoutBatch(info.userId, token);
                    }

                    LOG.error(messageSource.get(ErrorCode.E0009, new String[] { "workspaceId" }));
                }

            } else {
                for (String message : info.errors) {
                    LOG.error(message);
                }
            }

        } else {

            LOG.info(messageSource.get(ErrorCode.E0035, new String[] { "host, processServiceId, userID, "
                    + "pass, max_thread, thread_sleep, importPath, tempPath" }));
        }

    }

    private static void startUpdateProcess(String user, int processSId, int maxThread, int threadSleep, String host) {
        new UpdateProcessDataService().execute(user, processSId, maxThread, threadSleep, host);
    }

    private static AgentInfo parse(String processServiceId, String userId, String pass, String maxThread,
            String threadSleep, String importPath, String tempPath, String host) {

        AgentInfo param = new AgentInfo();

        int max = 0;
        int sleep = 0;
        int id = 0;
        String rawPassword = "";

        LinkedList<String> message = new LinkedList<>();

        max = toPositiveInteger(maxThread);
        if (max < 0) {
            message.add(messageSource.get(ErrorCode.E0011, new String[] { "maxThread" }));
        }

        sleep = toPositiveInteger(threadSleep);
        if (sleep < 0) {
            message.add(messageSource.get(ErrorCode.E0011, new String[] { "threadSleep" }));
        }

        id = toPositiveInteger(processServiceId);
        if (id < 0) {
            message.add(messageSource.get(ErrorCode.E0011, new String[] { "processServiceId" }));
        }

        if (!checkPathValid(importPath)) {
            message.add(messageSource.get(ErrorCode.E0029, new String[] { "importPath" }));
        }

        if (!checkPathValid(tempPath)) {
            message.add(messageSource.get(ErrorCode.E0029, new String[] { "tempPath" }));
        }

        try {
            rawPassword = CryptUtil.decryptData(pass);
        } catch (EarthException e) {
            LOG.error(e.getMessage(),e);
            message.add(messageSource.get(ErrorCode.E0001, new String[] { "password" }));
        }

        if (message.isEmpty()) {
            param.userId = userId;
            param.password = rawPassword;
            param.processServiceId = id;
            param.maxThread = max;
            param.threadSleep = sleep;
            param.importPath = Paths.get(importPath).normalize().toString();
            param.tempPath = Paths.get(tempPath).normalize().toString();
            param.host = host;

        }
        param.errors = message;

        return param;
    }

    private static boolean checkPathValid(String path) {
        boolean isExist = true;
        try {
            File file = new File(path);
            isExist = file.isDirectory() && file.exists();
        } catch (Exception e) {
            isExist = false;
        }
        return isExist;
    }

    private static int toPositiveInteger(String num) {
        int value = -1;
        try {
            value = Integer.parseInt(num);
        } catch (NumberFormatException er) {
        }
        return value;
    }

    private final class ScheduleTimeHelper {

        private static final int HOUR_PER_DAY = 24;
        private static final int MINUTE_IN_HOUR = 60;
        private static final int SECOND_IN_MINUTE = 60;

        private int dayInterval;
        private int hourInterval;
        private int minuteInterval;
        private int secondInterval;
        private int total;
        private Date nextTime;
        private Date startTime;
        private Date endTime;

        ScheduleTimeHelper(MgrSchedule schedule) throws Exception {

            dayInterval = Integer.parseInt(schedule.getRunIntervalDay());
            hourInterval = Integer.parseInt(schedule.getRunIntervalHour());
            minuteInterval = Integer.parseInt(schedule.getRunIntervalMinute());
            secondInterval = Integer.parseInt(schedule.getRunIntervalSecond());
            int total = dayInterval;
            total = total * HOUR_PER_DAY + hourInterval;
            total = total * MINUTE_IN_HOUR + minuteInterval;
            this.total = total * SECOND_IN_MINUTE + secondInterval;

            this.nextTime = DateUtil.convertStringSimpleDateFormat(schedule.getNextRunDate(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            this.startTime = DateUtil.convertStringSimpleDateFormat(schedule.getStartTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            if(EStringUtil.isNotEmpty(schedule.getEndTime())){
                this.endTime = DateUtil.convertStringSimpleDateFormat(schedule.getEndTime(),
                    Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            }
        }

        public int getTotalSecond() {

            return total;
        }

        public Date getStartTime() throws Exception {

            return startTime;
        };

        public Date getEndTime() throws Exception {

            return endTime;
        };

        public Date getNextTime() throws Exception {

            return nextTime;
        };

    }

    private static class AgentInfo {

        private int processServiceId;
        private String userId = "";
        private String password = "";
        private int maxThread;
        private int threadSleep;
        private String importPath = "";
        private String tempPath = "";
        private String host = "";

        private List<String> errors = new LinkedList<String>();

        public int getProcessServiceId() {
            return processServiceId;
        }

        public void setProcessServiceId(int processServiceId) {
            this.processServiceId = processServiceId;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public int getMaxThread() {
            return maxThread;
        }

        public void setMaxThread(int maxThread) {
            this.maxThread = maxThread;
        }

        public int getThreadSleep() {
            return threadSleep;
        }

        public void setThreadSleep(int threadSleep) {
            this.threadSleep = threadSleep;
        }

        public String getImportPath() {
            return importPath;
        }

        public void setImportPath(String importPath) {
            this.importPath = importPath;
        }

        public String getTempPath() {
            return tempPath;
        }

        public void setTempPath(String tempPath) {
            this.tempPath = tempPath;
        }

        public List<String> getErrors() {
            return errors;
        }

        public void setErrors(List<String> errors) {
            this.errors = errors;
        }

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }
    }
}
