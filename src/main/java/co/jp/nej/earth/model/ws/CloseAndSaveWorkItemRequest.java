package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class CloseAndSaveWorkItemRequest extends Request {
    @NotEmpty(message = "E0001,workspaceId")
    private String workspaceId;
    @NotEmpty(message = "E0001,workitemId")
    private String workItemId;

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
}
