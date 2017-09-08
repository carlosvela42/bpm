package co.jp.nej.earth.batch;

import java.util.Date;

import org.joda.time.DateTime;
import org.quartz.JobExecutionContext;
import org.quartz.listeners.JobListenerSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import co.jp.nej.earth.model.constant.Constant;
import co.jp.nej.earth.service.ScheduleService;
import co.jp.nej.earth.util.ApplicationContextUtil;
import co.jp.nej.earth.util.DateUtil;

public class AgentJobListener extends JobListenerSupport {

    private static final String NAME_LISTENER = "AgentJobListener";

    private String name = NAME_LISTENER;

    protected static final Logger LOG = LoggerFactory.getLogger(AgentBatch.class);

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void jobToBeExecuted(JobExecutionContext context) {
        super.jobToBeExecuted(context);
        ScheduleService scheduleService = ApplicationContextUtil.getApplicationContext().getBean(ScheduleService.class);

        // Retrieve parameters to calculate state of job
        String workspaceId = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_WORKSPACE_ID);
        String scheduleId = context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_SCHEDULE_ID);
        // String endTime =
        // context.getJobDetail().getJobDataMap().getString(Constant.AgentBatch.P_END_TIME);
        int intervalSecond = context.getJobDetail().getJobDataMap().getInt(Constant.AgentBatch.P_INTERVAL_SECOND);

        // Calculate the value of the next_run_time to update into the database
        Date nextFireTime = context.getNextFireTime();

        LOG.info("START the schedule " + scheduleId + " at " + formatDate(context.getFireTime())
            + " (next at " + formatDate(nextFireTime) + ")");

        try {
            String nextTime = "";
            if (nextFireTime != null) {

                // Update next_run_time
                nextTime = DateUtil.convertSimpleDateFormat(nextFireTime,
                        Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

            } else {
                // Update the next_run_time at the last fire
                DateTime fireTime = new DateTime(context.getFireTime());
                nextTime = fireTime.plusSeconds(intervalSecond).
                        toString(Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            }
            LOG.info("updateNextRunDate start");
            if(scheduleService.updateNextRunDateByScheduleId(workspaceId, scheduleId, nextTime)) {
                LOG.info("updateNextRunDate is succeed");
            } else {
                LOG.info("updateNextRunDate is failed");
            }

        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
        }

    }

    private String formatDate(Date date) {
        String value = "";
        if (date != null) {
            value = DateUtil.convertSimpleDateFormat(date, Constant.DatePattern.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        }
        return value;
    }
}
