package co.jp.nej.earth.model.ws;

import org.hibernate.validator.constraints.NotEmpty;

public class UnlockEventRequest extends Request {
    @NotEmpty(message = "E001,workspaceId")
    private String workspaceId;
    @NotEmpty(message = "E001,workItemId")
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

    public String getWorkItemId() {
        return workItemId;
    }

    public void setWorkItemId(String workItemId) {
        this.workItemId = workItemId;
    }
}
