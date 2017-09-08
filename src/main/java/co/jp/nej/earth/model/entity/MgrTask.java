package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrTask;

/**
 * @author p-tvo-sonta
 */
public class MgrTask extends BaseModel<MgrTask> {
    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    private String taskId;
    private String taskName;
    private String customTaskId;
    private String processId;

    public MgrTask() {
        this.setqObj(QMgrTask.newInstance());
    }

    public MgrTask(String taskId, String taskName, String customTaskId, String processId) {
        this();
        this.taskId = taskId;
        this.taskName = taskName;
        this.customTaskId = customTaskId;
        this.processId = processId;
    }

    /**
     * @return the taskId
     */
    public String getTaskId() {
        return taskId;
    }

    /**
     * @param taskId the taskId to set
     */
    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    /**
     * @return the taskName
     */
    public String getTaskName() {
        return taskName;
    }

    /**
     * @param taskName the taskName to set
     */
    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getCustomTaskId() {
        return customTaskId;
    }

    public void setCustomTaskId(String customTaskId) {
        this.customTaskId = customTaskId;
    }

    /**
     * @return the processId
     */
    public String getProcessId() {
        return processId;
    }

    /**
     * @param processId the processId to set
     */
    public void setProcessId(String processId) {
        this.processId = processId;
    }

}
