package co.jp.nej.earth.web.form;

/**
 * Form handle request param of Task
 *
 * @author DaoPQ
 * @version 1.0
 */
public class TaskForm {
    private String taskId;
    private String workItemId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }
}