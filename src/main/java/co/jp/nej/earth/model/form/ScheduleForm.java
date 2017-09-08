package co.jp.nej.earth.model.form;

import co.jp.nej.earth.contraints.NotEmptyAndValidSize;
import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.MgrSchedule;
import co.jp.nej.earth.model.constant.Constant;
import org.hibernate.validator.constraints.NotEmpty;

public class ScheduleForm extends BaseModel<MgrSchedule> {

    @NotEmpty(message = "E0001,schedule.id")
    private String scheduleId;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_LENGTH,
        messageNotEmpty = "E0001,schedule.hostname",
        messageLengthMax = "E0026,schedule.hostname,"+Constant.Regexp.MAX_LENGTH)
    private String hostName;

    @NotEmpty(message = "E0001,process")
    private String processId;

    @NotEmpty(message = "E0001,task.name")
    private String taskId;

    @NotEmpty(message = "E0001,process.service")
    private String processIServiceId;

    @NotEmpty(message = "E0001,schedule.startDateTime")
    private String startTime;

    private String endTime;

    private String enableDisable;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_8,
        messageNotEmpty = "E0001,schedule.day",
        messageLengthMax = "E0026,schedule.day,"+Constant.Regexp.MAX_8)
    private String runIntervalDay;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_2,
        messageNotEmpty = "E0001,schedule.hour",
        messageLengthMax = "E0026,schedule.hour,"+Constant.Regexp.MAX_2)
    private String runIntervalHour;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_2,
        messageNotEmpty = "E0001,schedule.minute",
        messageLengthMax = "E0026,schedule.minute,"+Constant.Regexp.MAX_2)
    private String runIntervalMinute;

    @NotEmptyAndValidSize(notEmpty = true, max = Constant.Regexp.MAX_2,
        messageNotEmpty = "E0001,schedule.second",
        messageLengthMax = "E0026,schedule.second,"+Constant.Regexp.MAX_2)
    private String runIntervalSecond;

    public String getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(String scheduleId) {
        this.scheduleId = scheduleId;
    }

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getProcessId() {
        return processId;
    }

    public void setProcessId(String processId) {
        this.processId = processId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getProcessIServiceId() {
        return processIServiceId;
    }

    public void setProcessIServiceId(String processIServiceId) {
        this.processIServiceId = processIServiceId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getEnableDisable() {
        return enableDisable;
    }

    public void setEnableDisable(String enableDisable) {
        this.enableDisable = enableDisable;
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
}
