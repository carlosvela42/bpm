package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class SearchWorkItemRequest extends Request {
    @NotEmpty(message = "E001,workspaceId")
    private String workspaceId;

    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;
    private String taskId;
    private String templateId;
    private Integer lastHistoryNo;
    private String lastUpdateTime;

    /**
     * @return the workspaceId
     */
    public String getWorkspaceId() {
        return workspaceId;
    }
    /**
     * @param workspaceId the workspaceId to set
     */
    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }
    /**
     * @return the workItemId
     */
    public String getWorkItemId() {
        return workItemId;
    }
    /**
     * @param workItemId the workItemId to set
     */
    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
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
     * @return the templateId
     */
    public String getTemplateId() {
        return templateId;
    }
    /**
     * @param templateId the templateId to set
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }
    /**
     * @return the lastHistoryNo
     */
    public Integer getLastHistoryNo() {
        return lastHistoryNo;
    }
    /**
     * @param lastHistoryNo the lastHistoryNo to set
     */
    public void setLastHistoryNo(Integer lastHistoryNo) {
        this.lastHistoryNo = lastHistoryNo;
    }
    /**
     * @return the lastUpdateTime
     */
    public String getLastUpdateTime() {
        return lastUpdateTime;
    }
    /**
     * @param lastUpdateTime the lastUpdateTime to set
     */
    public void setLastUpdateTime(String lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}
