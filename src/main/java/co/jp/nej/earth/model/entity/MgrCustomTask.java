package co.jp.nej.earth.model.entity;

import co.jp.nej.earth.model.BaseModel;
import co.jp.nej.earth.model.sql.QMgrCustomTask;

/**
 * Class MgrCustomTask
 *
 * @author DaoPQ
 * @version 1.0
 */
public class MgrCustomTask extends BaseModel<MgrCustomTask> {

    /**
     * Serial Version UID.
     */
    private static final long serialVersionUID = 1L;
    private String customTaskId;
    private String customTaskName;
    private String customTaskType;
    private String className;

    public MgrCustomTask() {
        this.setqObj(QMgrCustomTask.newInstance());
    }

    public MgrCustomTask(String taskId, String taskName, String taskType, String className) {
        this();
        this.customTaskId = taskId;
        this.customTaskName = taskName;
        this.customTaskType = taskType;
        this.className = className;
    }

    public String getCustomTaskId() {
        return customTaskId;
    }

    public void setCustomTaskId(String customTaskId) {
        this.customTaskId = customTaskId;
    }

    public String getCustomTaskName() {
        return customTaskName;
    }

    public void setCustomTaskName(String customTaskName) {
        this.customTaskName = customTaskName;
    }

    public String getClassName() {
        return className;
    }

    public String getCustomTaskType() {
        return customTaskType;
    }

    public void setCustomTaskType(String customTaskType) {
        this.customTaskType = customTaskType;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
