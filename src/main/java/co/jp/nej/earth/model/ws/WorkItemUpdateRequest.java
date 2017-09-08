package co.jp.nej.earth.model.ws;

import co.jp.nej.earth.model.WorkItem;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.Valid;

public class WorkItemUpdateRequest extends Request {
    @NotEmpty(message = "E001,workspaceId")
    private String workspaceId;
    @Valid
    private WorkItem workItem;

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
     * @return the workItem
     */
    public WorkItem getWorkItem() {
        return workItem;
    }

    /**
     * @param workItem the workItem to set
     */
    public void setWorkItem(WorkItem workItem) {
        this.workItem = workItem;
    }

}
