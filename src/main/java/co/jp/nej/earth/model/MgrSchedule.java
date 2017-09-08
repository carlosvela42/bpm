package co.jp.nej.earth.model;

import co.jp.nej.earth.model.entity.MgrTask;
import co.jp.nej.earth.model.enums.EnableDisable;
import co.jp.nej.earth.model.sql.QMgrSchedule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MgrSchedule extends BaseModel<MgrSchedule> {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(MgrSchedule.class);

    private String scheduleId;
    private String hostName;
    private String processIServiceId;
    private String taskId;
    private String startTime;
    private String endTime;
    private String enableDisable = EnableDisable.ENABLE.getId();
    private String nextRunDate;
    private String runIntervalDay;
    private String runIntervalHour;
    private String runIntervalMinute;
    private String runIntervalSecond;

    private MgrTask task = new MgrTask();

    public MgrSchedule() {
        LOG.debug("Call to blank contructor");
        this.setqObj(QMgrSchedule.newInstance());
    }

    public MgrSchedule(String scheduleId, String taskId,
            String hostName, String processIServiceId, String startTime, String endTime, String enableDisable,
        String nextRunDate) {
        this();
        this.scheduleId = scheduleId;
        this.taskId = taskId;
        this.hostName = hostName;
        this.processIServiceId = processIServiceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enableDisable = enableDisable;
        this.nextRunDate = nextRunDate;
    }

    public MgrSchedule(String scheduleId, String taskId, String hostName, String processIServiceId,
            String enableDisable, String startTime, String endTime, String nextRunDate, String runIntervalDay,
            String runIntervalHour, String runIntervalMinute, String runIntervalSecond, String lastUpdateTime) {
        this();
        this.scheduleId = scheduleId;
        this.taskId = taskId;
        this.hostName = hostName;
        this.processIServiceId = processIServiceId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.enableDisable = enableDisable;
        this.nextRunDate = nextRunDate;
        this.runIntervalDay = runIntervalDay;
        this.runIntervalHour = runIntervalHour;
        this.runIntervalMinute = runIntervalMinute;
        this.runIntervalSecond = runIntervalSecond;
        this.setLastUpdateTime(lastUpdateTime);
    }

    /**
     * @return the scheduleId
     */
    public String getScheduleId() {
        return scheduleId;
    }

    /**
     * @param scheduleId
     *            the scheduleId to set
     */
    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     *            the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the hostName
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * @param hostName
     *            the hostName to set
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * @return the processIServiceId
     */
    public String getProcessIServiceId() {
        return processIServiceId;
    }

    /**
     * @param processIServiceId
     *            the processIseviceId to set
     */
    public void setProcessIServiceId(String processIServiceId) {
        this.processIServiceId = processIServiceId;
    }

    /**
     * @return the startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * @param startTime
     *            the startTime to set
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * @param endTime
     *            the endTime to set
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEnableDisable() {
        return enableDisable;
    }

    public void setEnableDisable(String enableDisable) {
        this.enableDisable = enableDisable;
    }

    public String getNextRunDate() {
        return nextRunDate;
    }

    public void setNextRunDate(String nextRunDate) {
        this.nextRunDate = nextRunDate;
    }

    public String getRunIntervalDay() {
        return runIntervalDay;
    }

    public void setRunIntervalDay(String runIntervalDay) {
        this.runIntervalDay = runIntervalDay;
    }

    public String getRunIntervalHour() {
        return runIntervalHour;
    }

    public void setRunIntervalHour(String runIntervalHour) {
        this.runIntervalHour = runIntervalHour;
    }

    public String getRunIntervalMinute() {
        return runIntervalMinute;
    }

    public void setRunIntervalMinute(String runIntervalMinute) {
        this.runIntervalMinute = runIntervalMinute;
    }

    public String getRunIntervalSecond() {
        return runIntervalSecond;
    }

    public void setRunIntervalSecond(String runIntervalSecond) {
        this.runIntervalSecond = runIntervalSecond;
    }

    public MgrTask getTask() {
        return task;
    }

    public void setTask(MgrTask task) {
        this.task = task;
    }
}
